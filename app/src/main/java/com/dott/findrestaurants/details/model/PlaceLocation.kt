package com.dott.findrestaurants.details.model

data class PlaceLocation (
    val address : String,
    val country : String,
    val cross_street : String,
    val formatted_address : String,
    val locality : String,
    val postcode : String,
    val region : String
)
