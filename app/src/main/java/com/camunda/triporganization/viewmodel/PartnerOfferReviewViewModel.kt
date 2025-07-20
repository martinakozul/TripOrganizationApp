package com.camunda.triporganization.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.camunda.triporganization.model.PartnerOfferItem
import com.camunda.triporganization.model.PartnerOfferResponse
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.network.Network
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PartnerOfferReviewViewModel(
    application: Application,
) : AndroidViewModel(application) {
    val service = Network.tripService

    private val _offers = MutableStateFlow<OffersWrapper?>(null)
    val offers = _offers.asStateFlow()

    fun getOffers(processKey: Long) {
        viewModelScope.launch {
            val tripAsync = async { service.getTripInformation(processKey) }
            val offerResponse = async { service.getOffersForTrip(processKey) }

            _offers.update {
                OffersWrapper(
                    tripAsync.await().body(),
                    groupOffers(offerResponse.await()),
                )
            }
        }
    }

    fun rejectAllOffers(tripId: Long) {
        viewModelScope.launch {
            service.rejectOffersForTrip(tripId)
        }
    }

    fun acceptOffers(
        tripId: Long,
        transportOffer: List<PartnerOfferItem>,
        accommodationOffer: List<PartnerOfferItem>,
    ) {
        viewModelScope.launch {
            service.acceptOffersForTrip(tripId, transportOffer.map { it.id }, accommodationOffer.map { it.id })
        }
    }

    fun groupOffers(response: PartnerOfferResponse): GroupedPartnerOffers {
        val accommodationGrouped = response.accommodation.groupBy { it.cityId }
        val transportGrouped = response.transport.groupBy { it.cityId }

        return GroupedPartnerOffers(
            accommodation = accommodationGrouped,
            transport = transportGrouped,
        )
    }
}

data class GroupedPartnerOffers(
    val accommodation: Map<Long, List<PartnerOfferItem>>,
    val transport: Map<Long, List<PartnerOfferItem>>,
)

data class OffersWrapper(
    val trip: Trip?,
    val groupedPartnerOffers: GroupedPartnerOffers,
)
