package com.dott.findrestaurants.map.model

import com.dott.findrestaurants.map.viewmodel.RestosData
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface RestaurantsRepository
{
    fun getNearbyRestaurants(
                             categories : String,
                             ne : String,
                             sw : String,
                             limit : Int = 50) : Observable<RestaurantResult>

    fun getNearbyRestaurantsFromDb(swLat : Double, swLong : Double, neLat : Double, neLong: Double) : Single<List<RestosData>>

    fun addRestaurantsToDb(restos : List<RestosData>)
}