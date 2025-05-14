package com.camunda.triporganization.model

import kotlinx.serialization.Serializable

@Serializable
data class BasicTaskItem(
    val tripId: Long,
    val taskName: String,
    var tripName: String? = null
)