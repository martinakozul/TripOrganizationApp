package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.model.CityResponse
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TripCreateViewModel(
    application: Application,
) : AndroidViewModel(application) {
    val service = Network.tripService

    private val _tripDetails = MutableStateFlow<TripWrapper?>(null)
    val tripDetails = _tripDetails.asStateFlow()

    fun fetchTripDetails(id: Long) {
        viewModelScope.launch {
            val citiesAsync = async { service.getCities() }
            _tripDetails.update {
                TripWrapper(
                    null,
                    citiesAsync.await(),
                )
            }
        }
    }

    fun saveTripDetails(trip: Trip) {
        viewModelScope.launch {
            service.saveTripCreationData(trip.id, trip)
        }
    }

    fun createTrip(trip: Trip) {
        viewModelScope.launch {
            service.fillTripCreationData(processKey = trip.id, tripRequest = trip, true)
        }
    }
}

data class TripWrapper(
    val trip: Trip?,
    val cities: List<CityResponse>,
)
