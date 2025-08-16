package com.camunda.triporganization.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.R
import com.camunda.triporganization.helper.AppSingleton
import com.camunda.triporganization.helper.DateHelper
import com.camunda.triporganization.model.CitiesData
import com.camunda.triporganization.model.TransportationType
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.ui.theme.AppTypography
import com.camunda.triporganization.ui.theme.Colors.onPrimary
import com.camunda.triporganization.ui.theme.Colors.onPrimaryContainer
import com.camunda.triporganization.ui.theme.Colors.primaryContainer

@Composable
fun TripInformationCollapsible(
    trip: Trip,
    modifier: Modifier = Modifier,
) {
    var showTripInformation by remember { mutableStateOf(true) }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable {
                    showTripInformation = !showTripInformation
                }.shadow(2.dp)
                .background(primaryContainer)
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = AppTypography.labelLarge,
                color = onPrimaryContainer,
                text = "Trip Information",
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier =
                    Modifier
                        .padding(start = 4.dp)
                        .size(24.dp),
                imageVector = if (showTripInformation) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
            )
        }

        AnimatedVisibility(visible = showTripInformation) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        color = onPrimaryContainer,
                        style = AppTypography.bodyMedium,
                        text = "Trip name:",
                    )
                    Text(
                        style = AppTypography.labelLarge,
                        text = trip.tripName ?: "",
                    )
                }
                Text(
                    style = AppTypography.labelLarge,
                    text = "${DateHelper.convertMillisToDate(trip.tripStartDate)} - ${
                        DateHelper.convertMillisToDate(trip.tripEndDate)
                    }",
                )
                Text(
                    style = AppTypography.bodyMedium,
                    text = trip.cities.joinToString(" - ") { "${it.cityName} (${it.daysSpent})" },
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        color = onPrimaryContainer,
                        style = AppTypography.bodyMedium,
                        text = "Passengers range:",
                    )
                    Text(
                        style = AppTypography.labelLarge,
                        text = "${trip.minTravelers} - ${trip.maxTravelers} people",
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        color = onPrimaryContainer,
                        style = AppTypography.bodyMedium,
                        text = "Price:",
                    )
                    Text(
                        style = AppTypography.labelLarge,
                        text = if (trip.price != null) trip.price.toString() else "TBD",
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Image(
                        painter =
                            painterResource(
                                id =
                                    if (trip.transportation ==
                                        TransportationType.BUS
                                    ) {
                                        R.drawable.ic_bus
                                    } else {
                                        R.drawable.ic_plane
                                    },
                            ),
                        contentDescription = null,
                        modifier =
                            Modifier
                                .padding(start = 4.dp)
                                .size(24.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TripInformationCollapsiblePreview() {
    TripInformationCollapsible(
        trip =
            Trip(
                id = 1,
                tripName = "Milano Shopping Spree",
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
                            cityName = "Milano",
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
                            order = 3,
                            plan = "",
                            includedActivities = emptyList(),
                            extraActivities = emptyList(),
                        ),
                    ),
                minTravelers = 30,
                maxTravelers = 50,
                transportation = TransportationType.BUS,
                tripStartDate = System.currentTimeMillis(),
                tripEndDate = System.currentTimeMillis(),
                price = null,
                coordinatorId = AppSingleton.userId,
            ),
    )
}
