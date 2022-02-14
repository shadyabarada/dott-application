package com.dott.findrestaurants.details.model

import com.dott.findrestaurants.map.model.GeoCodes

data class RestaurantDetailsResult (
        val geocodes: GeoCodes,
        val categories: List<PlaceCategory>,
        val name : String,
        val location : PlaceLocation
)