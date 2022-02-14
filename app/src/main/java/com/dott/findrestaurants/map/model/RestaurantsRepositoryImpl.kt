package com.dott.findrestaurants.map.model

import com.dott.findrestaurants.map.dao.RestaurantsDao
import com.dott.findrestaurants.map.viewmodel.RestosData
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class RestaurantsRepositoryImpl(private val restaurantsApi: RestaurantsApi, private val restaurantsDao: RestaurantsDao) : RestaurantsRepository
{
    override fun getNearbyRestaurants( categories: String, ne : String, sw :String, limit: Int): Observable<RestaurantResult>
    {
        return restaurantsApi.getNearbyRestaurants( categories, ne, sw, limit).subscribeOn(Schedulers.io()).doOnNext{
            val restaurants = it.results
            val data = arrayListOf<RestosData>()
            for(resto in restaurants)
            {
                val id = resto.fsq_id
                val lat = resto.geocodes.main.latitude
                val long = resto.geocodes.main.longitude
                val name = resto.name
                val restoItem = RestosData(id, lat, long, name)
                data.add(restoItem)
            }
            addRestaurantsToDb(data)
        }.observeOn(AndroidSchedulers.mainThread())
    }

    override fun getNearbyRestaurantsFromDb(
        swLat: Double,
        swLong: Double,
        neLat: Double,
        neLong: Double
    ): Single<List<RestosData>> {

        return restaurantsDao.findWithBounds(swLat, swLong, neLat, neLong).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun addRestaurantsToDb(restos: List<RestosData>) {
         return restaurantsDao.add(restos)
    }
}