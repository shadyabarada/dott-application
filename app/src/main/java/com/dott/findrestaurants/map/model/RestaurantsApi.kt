package com.dott.findrestaurants.map.model

import com.dott.findrestaurants.utils.ConstantStrings
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsApi
{
    @GET(ConstantStrings.Services.GET_RESTAURANTS)
    fun getNearbyRestaurants
            (
             @Query("categories") categories : String,
             @Query("ne") northEastBound : String,
             @Query("sw") southWestBound : String,
             @Query("limit") limit : Int = 50,
    ) : Observable<RestaurantResult>
}