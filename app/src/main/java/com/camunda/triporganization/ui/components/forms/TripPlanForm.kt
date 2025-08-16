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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.R
import com.camunda.triporganization.model.CitiesData
import com.camunda.triporganization.model.TransportationType
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.ui.components.CustomButton
import com.camunda.triporganization.ui.components.CustomTopBar
import com.camunda.triporganization.ui.components.SubmitLoader
import com.camunda.triporganization.ui.components.TripInformationCollapsible
import com.camunda.triporganization.ui.theme.AppTypography
import com.camunda.triporganization.ui.theme.Colors.primaryContainer
import com.camunda.triporganization.ui.theme.Colors.surface

@Composable
fun TripPlanForm(
    trip: Trip?,
    onSubmitClicked: (List<CitiesData>) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (trip == null) return

    val cities = trip.cities.sortedBy { it.order }
    var activities by remember { mutableStateOf<Map<Long, List<TripActivity>>>(mutableMapOf()) }
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

    Scaffold(
        topBar = {
            CustomTopBar(title = "Define trip itinerary", onBackPressed = onBackPressed)
        },
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
        ) {

            TripInformationCollapsible(trip, modifier.background(surface).padding(bottom = 16.dp))

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
                        style = AppTypography.labelLarge,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                        text = city.cityName + "  (" + pluralStringResource(
                            id = R.plurals.nights,
                            count = (city.daysSpent - 1).coerceAtLeast(0),
                            (city.daysSpent - 1).coerceAtLeast(0)
                        ) + ")",
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

                    ActivityInput(
                        activities = activities.getOrElse(city.cityId, { emptyList() }),
                        addActivity = { activity ->
                            val mutableActivities = activities.toMutableMap()
                            val mutableCityActivities = activities.getOrElse(
                                city.cityId,
                                { emptyList() }).toMutableList()
                            mutableCityActivities.add(activity)
                            mutableActivities[city.cityId] = mutableCityActivities
                            activities = mutableActivities
                        }
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier.height(48.dp),
                    )
                }
            }

            CustomButton(
                enabled = false,
                text = "Submit itinerary",
                onClick = {
                    showLoader = true
                    onSubmitClicked(
                        cities.map { city ->
                            city.copy(
                                plan = tripPlan[city.order - 1].joinToString(";"),
                                includedActivities = activities.getOrElse(city.cityId, {emptyList()}).filter { it.isIncluded }.map { "${it.name} (€${it.price})" },
                                extraActivities = activities.getOrElse(city.cityId, {emptyList()}).filter { it.isIncluded.not() }.map { "${it.name} (€${it.price})" },
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

@Composable
private fun ActivityInput(
    activities: List<TripActivity>,
    addActivity: (TripActivity) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddActivity by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(top = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable { showAddActivity = true },
        ) {
            Text(
                style = AppTypography.labelLarge,
                text = "Add activity",
            )
            Icon(
                modifier =
                    Modifier
                        .padding(start = 4.dp)
                        .size(16.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
        }

        Text(
            style = AppTypography.labelLarge,
            modifier = Modifier.fillMaxWidth(),
            text = "Included activities:",
        )

        if (showAddActivity) {
            AddActivityDialog(
                tripActivity = null,
                onActivityCreated = { activity ->
                    addActivity(activity)
                    showAddActivity = false
                },
                onDismiss = {
                    showAddActivity = false
                },
            )
        }
    }

    activities.filter { it.isIncluded }.forEach {
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
        }
    }

    Text(
        style = AppTypography.labelLarge,
        modifier = Modifier.fillMaxWidth(),
        text = "Extra activities:",
    )


    activities.filter { it.isIncluded.not() }.forEach {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = it.name + "  €${it.price}")
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
                            cityName = "Zagreb",
                            daysSpent = 0,
                            order = 1,
                        ),
                        CitiesData(
                            cityId = 4,
                            cityName = "Milan",
                            daysSpent = 2,
                            order = 2,
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
