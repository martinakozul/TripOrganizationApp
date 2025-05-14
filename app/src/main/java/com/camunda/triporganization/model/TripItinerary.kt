package com.camunda.triporganization.model

import kotlinx.serialization.Serializable

@Serializable
data class TripItinerary(
    val tripPlan: List<String>,
    val includedActivities: List<String>,
    val extraActivities: List<String>,
    val note: String
)