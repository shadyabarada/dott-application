package com.dott.findrestaurants.map.viewmodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Restaurants")
@Parcelize
data class RestosData(
    @PrimaryKey val id : String,
    val lat : Double,
    val lon : Double,
    val name: String
) : Parcelable