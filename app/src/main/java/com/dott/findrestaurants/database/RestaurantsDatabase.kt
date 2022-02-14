package com.dott.findrestaurants.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dott.findrestaurants.map.dao.RestaurantsDao
import com.dott.findrestaurants.map.viewmodel.RestosData

@Database(
    entities = [RestosData::class],
    version = 1, exportSchema = false
)

abstract class RestaurantsDatabase : RoomDatabase() {
    abstract val restaurantsDao : RestaurantsDao
}