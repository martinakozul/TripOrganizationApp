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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
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
import com.camunda.triporganization.model.CitiesData
import com.camunda.triporganization.model.TransportationType
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.ui.components.CustomButton
import com.camunda.triporganization.ui.components.CustomTopBar
import com.camunda.triporganization.ui.components.SubmitLoader
import com.camunda.triporganization.ui.theme.Colors.primaryContainer

@Composable
fun TripPlanForm(
    trip: Trip?,
    onSubmitClicked: (List<CitiesData>) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (trip == null) return

    val cities = trip.cities.sortedBy { it.order }

    var tripPlan by remember {
        mutableStateOf(
            cities.map { item ->
                val parts = item.plan.split(";")
                val count = if (item.daysSpent == 0) 1 else item.daysSpent
                List(count) { parts }.flatten()
            },
        )
    }
    var showLoader by remember { mutableStateOf(false) }

    var includedActivities by remember { mutableStateOf<List<TripActivity>>(listOf()) }
    var extraActivities by remember { mutableStateOf<List<TripActivity>>(listOf()) }

    var showAddActivity by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Assign a guide", onBackPressed = onBackPressed)
        },
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
        ) {
            LazyColumn(
                modifier =
                    Modifier
                        .weight(1f)
                        .shadow(2.dp)
                        .background(primaryContainer),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp),
            ) {
                items(cities) { city ->

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = city.cityName + " (${city.daysSpent} nights)",
                    )

                    repeat(city.daysSpent.coerceAtLeast(1)) { i ->
                        OutlinedTextField(
                            value = tripPlan[city.order - 1][(i).coerceAtLeast(0)],
                            minLines = 5,
                            onValueChange = { newPlan ->
                                tripPlan =
                                    tripPlan.mapIndexed { outerIndex, dayPlans ->
                                        if (outerIndex == city.order - 1) {
                                            dayPlans.mapIndexed { innerIndex, plan ->
                                                if (innerIndex == i) newPlan else plan
                                            }
                                        } else {
                                            dayPlans
                                        }
                                    }
                            },
                            label = { Text("Itinerary") },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { showAddActivity = true },
                    ) {
                        Text(text = "Add activity")
                        Icon(
                            modifier =
                                Modifier
                                    .padding(start = 4.dp)
                                    .size(16.dp),
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                    }
                }

                item {
                    Text(modifier = Modifier.fillMaxWidth(), text = "Included activities:")
                }

                items(includedActivities) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = it.name + "€${it.price}")
                        Icon(
                            modifier =
                                Modifier
                                    .padding(start = 4.dp)
                                    .size(16.dp),
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                        )
                        Icon(
                            modifier =
                                Modifier
                                    .padding(start = 4.dp)
                                    .size(16.dp),
                            imageVector = Icons.Default.Create,
                            contentDescription = null,
                        )
                    }
                }

                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Extra activities:",
                    )
                }

                items(extraActivities) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = it.name + "€${it.price}")
                        Icon(
                            modifier =
                                Modifier
                                    .padding(start = 4.dp)
                                    .size(16.dp),
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                        )
                        Icon(
                            modifier =
                                Modifier
                                    .padding(start = 4.dp)
                                    .size(16.dp),
                            imageVector = Icons.Default.Create,
                            contentDescription = null,
                        )
                    }
                }

                item {
                    if (showAddActivity) {
                        AddActivityDialog(
                            tripActivity = null,
                            onActivityCreated = { activity ->
                                if (activity.isIncluded) {
                                    includedActivities = includedActivities + activity
                                } else {
                                    extraActivities = extraActivities + activity
                                }
                                showAddActivity = false
                            },
                            onDismiss = {
                                showAddActivity = false
                            },
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier.height(48.dp),
                    )
                }
            }

            CustomButton(
                text = "Submit itinerary",
                onClick = {
                    showLoader = true
                    onSubmitClicked(
                        cities.map { city ->
                            city.copy(
                                plan = tripPlan[city.order - 1].joinToString(";"),
                                includedActivities = includedActivities.map { "${it.name} (€${it.price})" },
                                extraActivities = extraActivities.map { "${it.name} (€${it.price})" },
                            )
                        },
                    )
                },
            )
        }

        AnimatedVisibility(
            visible = showLoader,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
        ) {
            SubmitLoader(
                lottieRes = R.raw.suitcase_lottie,
                text = "Submitting the itinerary suggestion",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripPlanFormPreview() {
    TripPlanForm(
        trip =
            Trip(
                id = 1,
                tripName = "Name",
                cities =
                    listOf(
                        CitiesData(
                            cityId = 5,
                            cityName = "Barcelona",
                            daysSpent = 2,
                            order = 2,
                        ),
                        CitiesData(
                            cityId = 4,
                            cityName = "Paris",
                            daysSpent = 2,
                            order = 1,
                        ),
                        CitiesData(
                            cityId = 7,
                            cityName = "Zagreb",
                            daysSpent = 0,
                            order = 3,
                        ),
                    ),
                minTravelers = 10,
                maxTravelers = 20,
                transportation = TransportationType.BUS,
                tripStartDate = System.currentTimeMillis(),
                tripEndDate = System.currentTimeMillis(),
                price = null,
                coordinatorId = "1",
            ),
        onSubmitClicked = {},
        onBackPressed = {},
    )
}
