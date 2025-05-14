package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.database.DatabaseProvider
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TripCreateViewModel(application: Application): AndroidViewModel(application) {

    val service = Network.tripService

    private val _tripDetails = MutableStateFlow<Trip?>(null)
    val tripDetails = _tripDetails.asStateFlow()

    fun fetchTripDetails(id: Long) {
        viewModelScope.launch {
            _tripDetails.update { service.getTripInformation(id).body() }
        }
    }

    fun saveTripDetails(trip: Trip) {
        viewModelScope.launch {
            service.saveTripCreationData(trip.id, trip)
        }
    }

    fun createTrip(trip: Trip) {
        viewModelScope.launch {
            service.fillTripCreationData(processKey = trip.id, tripRequest = trip)
        }
    }


}