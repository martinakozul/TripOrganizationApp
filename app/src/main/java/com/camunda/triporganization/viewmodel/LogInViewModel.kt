package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.helper.AppSingleton
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LogInViewModel(application: Application) : AndroidViewModel(application) {

    private val _roleState = MutableStateFlow(false)
    val roleState: StateFlow<Boolean> = _roleState.asStateFlow()

    val service = Network.tripService

    fun logIn(username: String, password: String) {
        viewModelScope.launch {
            val user = service.userLogIn(username, password)
            if (user.isSuccessful) {
                AppSingleton.userId = user.body()?.id
                AppSingleton.userRole = user.body()?.role
                _roleState.update { true }
            } else {
               // TODO show snackbar
            }

        }
    }
}