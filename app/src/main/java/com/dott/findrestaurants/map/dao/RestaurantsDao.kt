package com.dott.findrestaurants.map.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dott.findrestaurants.map.viewmodel.RestosData
import io.reactivex.Single

@Dao
interface RestaurantsDao {

    @Query("SELECT * FROM Restaurants")
    fun findAll () : Single<List<RestosData>>

    @Query("SELECT * FROM Restaurants WHERE lat >= :swLat and lon >= :swLong and lat <= :neLat and lon <= :neLong ")
    fun findWithBounds(
        swLat: Double,
        swLong: Double,
        neLat: Double,
        neLong: Double
    ): Single<List<RestosData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(restos: List<RestosData>)
}