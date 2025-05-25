package com.camunda.triporganization.model

import kotlinx.serialization.Serializable

@Serializable
data class CityResponse(
    val id: Long,
    val name: String
)