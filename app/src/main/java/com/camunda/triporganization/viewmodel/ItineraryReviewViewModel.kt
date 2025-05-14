package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.model.TripItinerary
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItineraryReviewViewModel(application: Application) : AndroidViewModel(application) {
    val service = Network.tripService

    private val _tripItinerary = MutableStateFlow<TripItinerary?>(null)
    val tripItinerary = _tripItinerary.asStateFlow()

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

    fun submitReview(processKey: Long, price: Double?, note: String?) {
        viewModelScope.launch {
            service.reviewTripItinerary(processKey, price, note)
        }
    }
}