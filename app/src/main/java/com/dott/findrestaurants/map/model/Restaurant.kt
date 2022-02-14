package com.dott.findrestaurants.map.model

data class Restaurant(
        val fsq_id : String,
        val geocodes : GeoCodes,
        val name : String
)