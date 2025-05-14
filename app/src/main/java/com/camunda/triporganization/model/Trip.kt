package com.camunda.triporganization.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey
    val id: Long,
    val tripName: String? = null,
    val cities: String? = null,
    val minTravelers: Int = 0,
    val maxTravelers: Int = 30,
    val transportation: TransportationType? = null,
    val tripStartDate: Long? = null,
    val tripEndDate: Long? = null,
    val price: Double? = null
)

enum class TransportationType {
    BUS, PLANE
}