package com.dott.findrestaurants.utils

class ConstantStrings
{
    object Keys
    {
        const val FS_PLACES_API_KEY = "fsq3sO5nUOp8oMP36gfDXG3iV8knEcs9bOwiznbMRA/XkHo="
        const val FOOD_CATEGORY = "13065"
    }

    object Services
    {
        const val BASE_URL = "https://api.foursquare.com/v3/"
        const val GET_RESTAURANTS = "places/search"
        const val GET_RESTAURANT_DETAILS = "places/{id}"
    }

    object Permissions
    {
        const val PERMISSON_LOCATION = 1
    }

}