package com.dott.findrestaurants.details.model

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsRepositoryImpl(private val detailsApi: DetailsApi) : DetailsRepository
{
    override fun getRestaurantDetails(fsqId: String): Observable<RestaurantDetailsResult>
    {
        return detailsApi.getRestaurantDetails(fsqId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}