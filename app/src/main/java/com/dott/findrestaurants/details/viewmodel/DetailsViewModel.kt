package com.dott.findrestaurants.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dott.findrestaurants.details.model.DetailsRepository
import com.dott.findrestaurants.details.model.RestaurantDetailsResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class DetailsViewModel(private val detailsRepository: DetailsRepository): ViewModel()
{
    private val disposables: CompositeDisposable = CompositeDisposable()
    val restoData = MutableLiveData<RestoDetailsData>()

    fun getRestaurantDetails(fsqId : String)
    {
        disposables.add(detailsRepository.getRestaurantDetails(fsqId).subscribeWith(object : DisposableObserver<RestaurantDetailsResult>(){
            override fun onNext(t: RestaurantDetailsResult)
            {
                val details = RestoDetailsData(
                    t.name,
                    "${t.geocodes.main.longitude},${t.geocodes.main.latitude}",
                    t.location.formatted_address,
                    "${t.categories.get(0).icon.prefix}${t.categories.get(0).icon.suffix}",
                    t.location.postcode
                )
                restoData.value = details
            }

            override fun onError(e: Throwable) {
                System.out.println("Error")
            }

            override fun onComplete() {

            }

            override fun onStart() {
                super.onStart()
            }
        }))
    }
}