package com.camunda.triporganization.ui.components.forms

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.R
import com.camunda.triporganization.helper.AppSingleton
import com.camunda.triporganization.model.CitiesData
import com.camunda.triporganization.model.PartnerOfferItem
import com.camunda.triporganization.model.TransportationType
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.ui.components.CustomButton
import com.camunda.triporganization.ui.components.CustomTopBar
import com.camunda.triporganization.ui.components.SubmitLoader
import com.camunda.triporganization.ui.components.TripInformationCollapsible
import com.camunda.triporganization.ui.theme.Colors.onPrimary
import com.camunda.triporganization.ui.theme.Colors.onSurface
import com.camunda.triporganization.ui.theme.Colors.primary
import com.camunda.triporganization.ui.theme.Colors.primaryContainer
import com.camunda.triporganization.ui.theme.Colors.surface
import kotlin.time.Duration.Companion.days

@Composable
fun PartnerOfferReviewScreen(
    trip: Trip?,
    transportOffers: Map<Long, List<PartnerOfferItem>>,
    accommodationOffers: Map<Long, List<PartnerOfferItem>>,
    onOffersAccepted: (List<PartnerOfferItem>, List<PartnerOfferItem>) -> Unit,
    onOffersRejected: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var transport by remember { mutableStateOf<PartnerOfferItem?>(null) }
    var accommodation by remember { mutableStateOf<Map<Long, PartnerOfferItem>>(emptyMap()) }
    var showLoader by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Pick offer",
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(surface)
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            trip?.let {
                TripInformationCollapsible(it)
            }
            Column(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .shadow(2.dp)
                            .background(primaryContainer)
                            .padding(vertical = 8.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        text = "Transport offers",
                    )

                    if (trip?.transportation == TransportationType.BUS) {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(onPrimary)
                                    .width(200.dp)
                                    .padding(vertical = 4.dp, horizontal = 8.dp),
                        ) {
                            transportOffers.forEach { cityOffers ->
                                cityOffers.value.forEachIndexed { i, offer ->
                                    Row(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    transport = offer
                                                }.background(
                                                    if (offer == transport) {
                                                        primary
                                                    } else {
                                                        onPrimary
                                                    },
                                                ).padding(vertical = 8.dp, horizontal = 4.dp),
                                    ) {
                                        Text(
                                            style = MaterialTheme.typography.bodyMedium,
                                            text = offer.partnerName,
                                            color =
                                                if (offer == transport) {
                                                    onPrimary
                                                } else {
                                                    onSurface
                                                },
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            style = MaterialTheme.typography.labelLarge,
                                            text = offer.pricePerPerson.toString() + "€",
                                            color =
                                                if (offer == transport) {
                                                    onPrimary
                                                } else {
                                                    onSurface
                                                },
                                        )
                                    }
                                    if (i != cityOffers.value.lastIndex) {
                                        HorizontalDivider(modifier = Modifier.fillMaxWidth())
                                    }
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            transportOffers.forEach { cityOffers ->
                                Column(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(onPrimary)
                                            .width(200.dp)
                                            .padding(vertical = 4.dp, horizontal = 8.dp),
                                ) {
                                    Text(
                                        style = MaterialTheme.typography.labelLarge,
                                        text = "to " + cityOffers.value.firstOrNull()?.cityName,
                                    )
                                    cityOffers.value.forEachIndexed { i, offer ->
                                        Row(
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        transport = offer
                                                    }.background(
                                                        if (offer == transport) {
                                                            primary
                                                        } else {
                                                            onPrimary
                                                        },
                                                    ).padding(vertical = 8.dp, horizontal = 4.dp),
                                        ) {
                                            Text(
                                                style = MaterialTheme.typography.bodyMedium,
                                                text = offer.partnerName,
                                                color =
                                                    if (offer == transport) {
                                                        onPrimary
                                                    } else {
                                                        onSurface
                                                    },
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            Text(
                                                style = MaterialTheme.typography.labelLarge,
                                                text = offer.pricePerPerson.toString() + "€",
                                                color =
                                                    if (offer == transport) {
                                                        onPrimary
                                                    } else {
                                                        onSurface
                                                    },
                                            )
                                        }
                                        if (i != cityOffers.value.lastIndex) {
                                            HorizontalDivider(modifier = Modifier.fillMaxWidth())
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .shadow(2.dp)
                            .background(primaryContainer)
                            .padding(vertical = 8.dp),
                ) {
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = "Accommodation offers",
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        accommodationOffers.forEach { cityOffers ->
                            Column(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(onPrimary)
                                        .width(200.dp)
                                        .padding(vertical = 4.dp, horizontal = 8.dp),
                            ) {
                                Text(
                                    style = MaterialTheme.typography.labelLarge,
                                    text = cityOffers.value.firstOrNull()?.cityName ?: "",
                                )
                                cityOffers.value.forEachIndexed { i, offer ->
                                    Row(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    accommodation =
                                                        accommodation.toMutableMap().apply {
                                                            this[cityOffers.key] = offer
                                                        }
                                                }.background(
                                                    if (offer == accommodation[cityOffers.key]) {
                                                        primary
                                                    } else {
                                                        onPrimary
                                                    },
                                                ).padding(vertical = 8.dp, horizontal = 4.dp),
                                    ) {
                                        Text(
                                            style = MaterialTheme.typography.bodyMedium,
                                            text = offer.partnerName,
                                            color =
                                                if (offer == accommodation[cityOffers.key]) {
                                                    onPrimary
                                                } else {
                                                    onSurface
                                                },
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            style = MaterialTheme.typography.labelLarge,
                                            text = offer.pricePerPerson.toString() + "€",
                                            color =
                                                if (offer == accommodation[cityOffers.key]) {
                                                    onPrimary
                                                } else {
                                                    onSurface
                                                },
                                        )
                                    }
                                    if (i != cityOffers.value.lastIndex) {
                                        Box(
                                            modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .heightIn(1.dp)
                                                    .background(Color.LightGray),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            CustomButton(
                text = "Accept selected offers",
                enabled = true, // transport != null && accommodation.keys.size == accommodationOffers.keys.size,
                onClick = {
                    onOffersAccepted(listOf(transport!!), accommodation.values.toList())
                    showLoader = true
                },
            )

            HorizontalDivider()

            CustomButton(
                text = "Reject all offers",
                onClick = {
                    onOffersRejected()
                    showLoader = true
                },
            )
        }

        AnimatedVisibility(
            visible = showLoader,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
        ) {
            SubmitLoader(
                lottieRes = R.raw.card_lottie,
                text = "Letting the partners know",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PartnerOfferReviewScreenPreview() {
    PartnerOfferReviewScreen(
        accommodationOffers =
            mapOf(
                1L to
                    listOf(
                        PartnerOfferItem(
                            id = 1,
                            partnerId = 1,
                            processKey = 2,
                            partnerName = "Royal Victoria Hotel",
                            pricePerPerson = 500,
                            cityId = 2,
                            cityName = "Pisa",
                        ),
                        PartnerOfferItem(
                            id = 1,
                            partnerId = 1,
                            processKey = 2,
                            partnerName = "AC Hotel Pisa",
                            pricePerPerson = 300,
                            cityId = 2,
                            cityName = "Pisa",
                        ),
                        PartnerOfferItem(
                            id = 1,
                            partnerId = 1,
                            processKey = 2,
                            partnerName = "Hotel La Pace",
                            pricePerPerson = 220,
                            cityId = 2,
                            cityName = "Pisa",
                        ),
                    ),
                2L to
                    listOf(
                        PartnerOfferItem(
                            id = 1,
                            partnerId = 1,
                            processKey = 2,
                            partnerName = "Combo",
                            pricePerPerson = 100,
                            cityId = 2,
                            cityName = "Milan",
                        ),
                        PartnerOfferItem(
                            id = 1,
                            partnerId = 1,
                            processKey = 2,
                            partnerName = "Sheraton",
                            pricePerPerson = 250,
                            cityId = 2,
                            cityName = "Milan",
                        ),
                    ),
            ),
        transportOffers =
            mapOf(
                1L to
                    listOf(
                        PartnerOfferItem(
                            id = 1,
                            partnerId = 1,
                            processKey = 2,
                            partnerName = "Flixbus",
                            pricePerPerson = 70,
                            cityId = 2,
                            cityName = "Paris",
                        ),
                        PartnerOfferItem(
                            id = 1,
                            partnerId = 1,
                            processKey = 2,
                            partnerName = "Arriva",
                            pricePerPerson = 100,
                            cityId = 2,
                            cityName = "Paris",
                        ),
                    ),
            ),
        onOffersAccepted = { _, _ -> },
        onOffersRejected = {},
        onBackPressed = {},
        trip =
            Trip(
                id = 1,
                tripName = "Italian summer",
                cities =
                    listOf(
                        CitiesData(
                            cityId = 1,
                            cityName = "Zagreb",
                            daysSpent = 0,
                            order = 1,
                            plan = "",
                            includedActivities = emptyList(),
                            extraActivities = emptyList(),
                        ),
                        CitiesData(
                            cityId = 1,
                            cityName = "Pisa",
                            daysSpent = 2,
                            order = 2,
                            plan = "",
                            includedActivities = emptyList(),
                            extraActivities = emptyList(),
                        ),
                        CitiesData(
                            cityId = 1,
                            cityName = "Milan",
                            daysSpent = 2,
                            order = 2,
                            plan = "",
                            includedActivities = emptyList(),
                            extraActivities = emptyList(),
                        ),
                        CitiesData(
                            cityId = 1,
                            cityName = "Zagreb",
                            daysSpent = 0,
                            order = 2,
                            plan = "",
                            includedActivities = emptyList(),
                            extraActivities = emptyList(),
                        ),
                    ),
                minTravelers = 10,
                maxTravelers = 20,
                transportation = TransportationType.BUS,
                tripStartDate = System.currentTimeMillis(),
                tripEndDate = System.currentTimeMillis().plus(4.days.inWholeMilliseconds),
                price = null,
                coordinatorId = AppSingleton.userId,
            ),
    )
}
