package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.helper.AppSingleton
import com.camunda.triporganization.model.UserLogInResponse
import com.camunda.triporganization.model.UserRole
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val _roleState = MutableStateFlow(false)
    val roleState: StateFlow<Boolean> = _roleState.asStateFlow()

    val service = Network.tripService

    fun logIn(username: String) {
        viewModelScope.launch {
            val user =
                service.userLogIn(
                    UserLogInResponse(
                        AppSingleton.userId ?: "",
                        username,
                        AppSingleton.userRole ?: UserRole.GUIDE,
                    ),
                )
            if (user.isSuccessful) {
                _roleState.update { true }
            } else {
                // TODO show snackbar
            }
        }
    }
}
