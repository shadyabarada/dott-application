package com.dott.findrestaurants.koin

import com.dott.findrestaurants.details.model.DetailsApi
import com.dott.findrestaurants.map.model.RestaurantsApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun provideRestaurantsApi(retrofit: Retrofit): RestaurantsApi {
        return retrofit.create(RestaurantsApi::class.java)
    }

    fun provideDetailsApi(retrofit: Retrofit): DetailsApi {
        return retrofit.create(DetailsApi::class.java)
    }

    single { provideRestaurantsApi(get()) }

    single { provideDetailsApi((get())) }
}