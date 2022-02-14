package com.dott.findrestaurants.network

import com.dott.findrestaurants.utils.ConstantStrings
import okhttp3.Interceptor
import okhttp3.Response

class RestaurantsInterceptor : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
        val request = chain
                .request()
                .newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", ConstantStrings.Keys.FS_PLACES_API_KEY)
                .build()
        return chain.proceed(request)
    }
}