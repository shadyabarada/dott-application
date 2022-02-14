package com.dott.findrestaurants.koin

import com.dott.findrestaurants.details.viewmodel.DetailsViewModel
import com.dott.findrestaurants.map.viewmodel.RestaurantsMapViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { RestaurantsMapViewModel(get())}
    viewModel { DetailsViewModel(get()) }

}