package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.model.TripItinerary
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TripItineraryViewModel(application: Application) : AndroidViewModel(application) {
    val service = Network.tripService

    private val _tripItinerary = MutableStateFlow<TripItinerary?>(null)
    val tripItinerary = _tripItinerary.asStateFlow()

    private val _tripInformation = MutableStateFlow<Trip?>(null)
    val tripInformation = _tripInformation.asStateFlow()

    private val _citiesWithDays = MutableStateFlow<List<Pair<String, Int>>>(listOf())
    val citiesWithDays = _citiesWithDays.asStateFlow()

    fun fetchTripItinerary(processKey: Long) {
        viewModelScope.launch {
            _tripItinerary.update {
                val tripItinerary = service.getTripItinerary(processKey)
                if (tripItinerary.isSuccessful) {
                    tripItinerary.body()
                } else {
                    null
                }
            }
        }
    }

    fun fetchTripInformation(processKey: Long) {
        viewModelScope.launch {
            val tripInfo = service.getTripInformation(processKey)
            val regex = """([A-Za-z]+) \((\d+)\)""".toRegex()

            val citiesWithDaysData = regex.findAll(tripInfo.body()?.cities ?: "")
                .map { matchResult ->
                    val city = matchResult.groupValues[1]
                    val number = matchResult.groupValues[2].toInt()
                    city to number
                }
                .toList()

            _tripInformation.update { tripInfo.body() }
            _citiesWithDays.update { citiesWithDaysData }
        }
    }

    fun submitTripItinerary(processKey: Long, tripItinerary: TripItinerary) {
        viewModelScope.launch {
            service.fillTripItinerary(processKey, tripItinerary)
        }
    }
}