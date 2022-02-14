package com.dott.findrestaurants.koin

import com.dott.findrestaurants.details.model.DetailsApi
import com.dott.findrestaurants.details.model.DetailsRepository
import com.dott.findrestaurants.details.model.DetailsRepositoryImpl
import com.dott.findrestaurants.map.dao.RestaurantsDao
import com.dott.findrestaurants.map.model.RestaurantsApi
import com.dott.findrestaurants.map.model.RestaurantsRepository
import com.dott.findrestaurants.map.model.RestaurantsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    fun provideRestaurantsRepository(api: RestaurantsApi, restaurantsDao: RestaurantsDao): RestaurantsRepository {
        return RestaurantsRepositoryImpl(api, restaurantsDao)
    }

    fun provideDetailsRepository(api: DetailsApi): DetailsRepository {
        return DetailsRepositoryImpl(api)
    }

    single { provideRestaurantsRepository(get(), get()) }

    single { provideDetailsRepository(get()) }

}