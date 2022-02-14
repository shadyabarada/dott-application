package com.dott.findrestaurants.details.model

import com.dott.findrestaurants.utils.ConstantStrings
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApi
{
    @GET(ConstantStrings.Services.GET_RESTAURANT_DETAILS)
    fun getRestaurantDetails(@Path("id") fsqId : String) : Observable<RestaurantDetailsResult>
}