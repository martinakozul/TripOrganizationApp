package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AssignGuideViewModel(application: Application): AndroidViewModel(application) {
    val service = Network.tripService

    private val _availableGuides = MutableStateFlow<List<String>>(emptyList())
    val availableGuides = _availableGuides.asStateFlow()

    fun getOffers(processKey: Long) {
        viewModelScope.launch {
            _availableGuides.update { service.getAvailableGuides(processKey) }
        }
    }

    fun assignTourGuide(tripId: Long, guide: String) {
        viewModelScope.launch {
            service.assignTourGuide(tripId, guide)
        }
    }
}