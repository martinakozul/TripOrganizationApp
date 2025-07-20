package com.camunda.triporganization.model

import com.camunda.triporganization.helper.AppSingleton
import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    val id: Long,
    val tripName: String? = null,
    val cities: List<CitiesData> = listOf(),
    val minTravelers: Int = 0,
    val maxTravelers: Int = 30,
    val transportation: TransportationType? = null,
    val tripStartDate: Long? = null,
    val tripEndDate: Long? = null,
    val price: Double? = null,
    val coordinatorId: String? = AppSingleton.userId,
)

@Serializable
data class CitiesData(
    val cityId: Long,
    val cityName: String,
    val daysSpent: Int,
    val order: Int,
    var plan: String = "",
    var includedActivities: List<String> = emptyList(),
    var extraActivities: List<String> = emptyList(),
)

enum class TransportationType {
    BUS,
    PLANE,
}
