package com.dott.findrestaurants.map.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dott.findrestaurants.map.model.RestaurantResult
import com.dott.findrestaurants.map.model.RestaurantsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class RestaurantsMapViewModel(private val restaurantsRepository: RestaurantsRepository) :
    ViewModel() {
    private val disposables: CompositeDisposable = CompositeDisposable()
    val restos = MutableLiveData<List<RestosData>>()
    val errorMessage = MutableLiveData<String>()

    fun getNearbyRestaurants(
        categories: String,
        neLat: Double,
        neLong: Double,
        swLat: Double,
        swLong: Double,
        limit: Int = 50
    ) {
        val formattedNe = "${neLat},${neLong}"
        val formattedSw = "${swLat},${swLong}"

        disposables.add(
            restaurantsRepository.getNearbyRestaurantsFromDb(
                swLat,
                swLong,
                neLat,
                neLong
            ).subscribe { result ->

                val cachedData = result
                if (!cachedData.isNullOrEmpty() && cachedData.size > 10) {
                    restos.value = cachedData
                } else {
                    getDataFromService(categories, formattedNe, formattedSw)
                }
            })
    }

    private fun getDataFromService(categories: String, ne: String, sw: String, limit: Int = 50) {
        disposables.add(
            restaurantsRepository.getNearbyRestaurants(categories, ne, sw, limit)
                .subscribeWith(object : DisposableObserver<RestaurantResult>() {
                    override fun onNext(result: RestaurantResult) {
                        val restaurants = result.results
                        val data = arrayListOf<RestosData>()
                        restaurants?.let {
                            for (resto in restaurants) {
                                val id = resto.fsq_id
                                val lat = resto.geocodes.main.latitude
                                val long = resto.geocodes.main.longitude
                                val name = resto.name
                                val restoItem = RestosData(id, lat, long, name)
                                data.add(restoItem)
                            }
                        }
                        restos.value = data
                    }

                    override fun onError(e: Throwable) {
                        errorMessage.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }

                    override fun onStart() {
                    }
                })
        )
    }
}