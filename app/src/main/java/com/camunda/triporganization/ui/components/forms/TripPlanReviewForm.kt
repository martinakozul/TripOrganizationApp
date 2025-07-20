package com.camunda.triporganization.ui.components.forms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.R
import com.camunda.triporganization.helper.AppSingleton
import com.camunda.triporganization.model.CitiesData
import com.camunda.triporganization.model.TransportationType
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.ui.components.CustomButton
import com.camunda.triporganization.ui.components.CustomCheckbox
import com.camunda.triporganization.ui.components.CustomTopBar
import com.camunda.triporganization.ui.components.SubmitLoader
import com.camunda.triporganization.ui.components.TripInformationCollapsible
import com.camunda.triporganization.ui.theme.Colors.primaryContainer

@Composable
fun TripPlanReviewForm(
    trip: Trip?,
    onPublishClicked: (Double) -> Unit,
    onRejectClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (trip == null) return

    var note by remember { mutableStateOf("") }
    var price by remember { mutableStateOf<Double?>(null) }
    var isApproved by remember { mutableStateOf(false) }

    var showTripInfo by remember { mutableStateOf(false) }
    var showTripItinerary by remember { mutableStateOf(true) }
    var showLoader by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Review itinerary",
                onBackPressed = onBackPressed
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TripInformationCollapsible(trip = trip)

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.clickable {
                        showTripItinerary = !showTripItinerary
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Trip itinerary")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(24.dp),
                        imageVector = if (showTripItinerary) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

                AnimatedVisibility(visible = showTripItinerary) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = trip.cities.joinToString("\n") { it.plan }
                        )
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            text = "Included activities"
                        )
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = trip.cities.map { it.includedActivities }.joinToString("\n")
                        )
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            text = "Optional activities"
                        )
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = trip.cities.map { it.extraActivities }.joinToString("\n")
                        )
                    }
                }
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    enabled = isApproved.not() && showLoader.not(),
                    value = if (isApproved) "" else note,
                    minLines = 5,
                    onValueChange = { note = it },
                    label = { Text("Note for the guide") },
                    modifier = Modifier.fillMaxWidth()
                )

                CustomCheckbox(
                    enabled = showLoader.not(),
                    onCheckedChanged = {
                        isApproved = !isApproved
                    },
                    isChecked = isApproved,
                    label = "Approve submitted trip plan"
                )

                if (isApproved) {
                    OutlinedTextField(
                        enabled = showLoader.not(),
                        value = (price ?: "").toString(),
                        onValueChange = { price = it.toDoubleOrNull() ?: 0.0 },
                        label = { Text("Price per person") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            CustomButton(
                text = if (isApproved) "Publish trip" else "Send back to guide",
                onClick = {
                    if (isApproved) onPublishClicked(price ?: 100.0) else onRejectClicked(note)
                    showLoader = true
                },
                enabled = (!isApproved && note.isNotEmpty()) || (price != null && isApproved)
            )
        }
        AnimatedVisibility(
            visible = showLoader,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            SubmitLoader(
                lottieRes = R.raw.suitcase_lottie,
                text = if (isApproved) "All set! Sit back and relax while the trip is being published" else "Letting the guide know..."
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripPlanReviewFormPreview() {
    TripPlanReviewForm(
        onRejectClicked = {},
        onPublishClicked = {},
        trip = Trip(
            id = 1,
            tripName = "test trip",
            cities = listOf(
                CitiesData(
                    cityId = 1,
                    cityName = "lala",
                    daysSpent = 2,
                    order = 1,
                    plan = "dflp\nkjsdka\najsdkajsd",
                    includedActivities = emptyList(),
                    extraActivities = emptyList()
                ),
                CitiesData(
                    cityId = 1,
                    cityName = "sd",
                    daysSpent = 2,
                    order = 2,
                    plan = "dflp\nkjsdka\najsdkajsd",
                    includedActivities = emptyList(),
                    extraActivities = emptyList()
                ),
            ),
            minTravelers = 10,
            maxTravelers = 20,
            transportation = TransportationType.PLANE,
            tripStartDate = System.currentTimeMillis(),
            tripEndDate = System.currentTimeMillis(),
            price = null,
            coordinatorId = AppSingleton.userId
        ),
        onBackPressed = {}
    )
}