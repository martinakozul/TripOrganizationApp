package com.camunda.triporganization.model

import kotlinx.serialization.Serializable

@Serializable
data class PartnerOfferResponse(
    val transport: List<PartnerOfferItem> = emptyList(),
    val accommodation: List<PartnerOfferItem> = emptyList()
)

@Serializable
data class PartnerOfferItem(
    val partnerId: Long,
    val processKey: Long,
    val partnerName: String,
    val pricePerPerson: Int
)