package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.model.PartnerOfferItem
import com.camunda.triporganization.model.PartnerOfferResponse
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PartnerOfferReviewViewModel(application: Application): AndroidViewModel(application) {
    val service = Network.tripService

    private val _offers = MutableStateFlow<PartnerOfferResponse?>(null)
    val offers = _offers.asStateFlow()

    fun getOffers(processKey: Long) {
        viewModelScope.launch {
            _offers.update { service.getOffersForTrip(processKey) }
        }
    }

    fun rejectAllOffers(tripId: Long) {
        viewModelScope.launch {
            service.rejectOffersForTrip(tripId)
        }
    }

    fun acceptOffers(tripId: Long, transportOffer: PartnerOfferItem, accommodationOffer: PartnerOfferItem) {
        viewModelScope.launch {
            service.acceptOffersForTrip(tripId, transportOffer.partnerId, accommodationOffer.partnerId)
        }
    }
}