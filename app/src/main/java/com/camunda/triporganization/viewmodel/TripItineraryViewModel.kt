package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.model.CitiesData
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.model.TripItinerary
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TripItineraryViewModel(application: Application) : AndroidViewModel(application) {
    val service = Network.tripService

    private val _tripInformation = MutableStateFlow<Trip?>(null)
    val tripInformation = _tripInformation.asStateFlow()

    fun fetchTripData(processKey: Long) {
        viewModelScope.launch {
            _tripInformation.update {
                val tripItinerary = service.getTripInformation(processKey)
                if (tripItinerary.isSuccessful) {
                    tripItinerary.body()
                } else {
                    null
                }
            }
        }
    }

    fun submitTripItinerary(processKey: Long, tripItinerary: List<CitiesData>) {
        viewModelScope.launch {
            service.fillTripItinerary(processKey, tripItinerary)
        }
    }
}