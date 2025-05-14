package com.camunda.triporganization.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLogInResponse(
    val id: Long,
    val role: UserRole
)

enum class UserRole {
    NOT_LOGGED_IN, COORDINATOR, GUIDE
}
