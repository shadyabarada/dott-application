package com.dott.findrestaurants.details.model

import io.reactivex.Observable


interface DetailsRepository
{
    fun getRestaurantDetails(fsqId : String) : Observable<RestaurantDetailsResult>
}