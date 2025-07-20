package com.camunda.triporganization.ui.components.forms

import CustomDropdownMenu
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.R
import com.camunda.triporganization.helper.DateHelper
import com.camunda.triporganization.model.CitiesData
import com.camunda.triporganization.model.TransportationType
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.ui.components.CustomButton
import com.camunda.triporganization.ui.components.CustomTopBar
import com.camunda.triporganization.ui.components.DateRangePickerModal
import com.camunda.triporganization.ui.components.SubmitLoader
import com.camunda.triporganization.ui.theme.Colors.primaryContainer
import com.camunda.triporganization.viewmodel.TripWrapper
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TripCreationForm(
    tripWrapper: TripWrapper?,
    tripId: Long,
    onCreateTrip: (Trip) -> Unit,
    onSaveChanges: (Trip) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val trip = tripWrapper?.trip

    var showLoader by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(trip?.tripName ?: "") }

    val cities = remember { mutableStateListOf<CitiesData>() }

    var minTravelers by remember { mutableIntStateOf(trip?.minTravelers ?: 0) }
    var maxTravelers by remember { mutableIntStateOf(trip?.maxTravelers ?: 0) }
    var datePickerShown by remember { mutableStateOf(false) }
    var tripDate by remember { mutableStateOf<Pair<Long, Long>?>(null) }
    var transportationType by remember { mutableStateOf(trip?.transportation) }

    var createEnabled by remember { mutableStateOf(false) }

    var showDaysInputDialog by remember { mutableStateOf<Int?>(null) }
    var tripLength by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Fill trip data",
                onBackPressed = onBackPressed,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(8.dp)
                        .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                        .background(
                            primaryContainer,
                            shape = RoundedCornerShape(8.dp),
                        ).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Trip name") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Column(
                    modifier = Modifier.padding(bottom = 12.dp),
                ) {
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        cities.forEachIndexed { index, city ->
                            Text(
                                text = city.cityName,
                                modifier =
                                    Modifier.combinedClickable(
                                        onClick = { showDaysInputDialog = index },
                                        onLongClick = {
                                            cities.remove(city)
                                        },
                                        onLongClickLabel = "",
                                    ),
                            )
                            if (index != cities.lastIndex) {
                                Text("- ")
                            }
                        }
                        CustomDropdownMenu(
                            label = "Add city",
                            menuItemData = tripWrapper?.cities?.map { it.name } ?: listOf(),
                            onItemSelected = { name ->
                                tripWrapper?.cities?.firstOrNull { it.name == name }?.let {
                                    cities.add(
                                        CitiesData(
                                            cityId = it.id,
                                            cityName = it.name,
                                            daysSpent = 0,
                                            order = cities.lastIndex + 1,
                                        ),
                                    )
                                }
                            },
                        )
                    }
                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        text =
                            "Minimum days to fill: " +
                                if (tripDate == null) {
                                    0
                                } else {
                                    TimeUnit.MILLISECONDS.toDays(tripDate!!.second - tripDate!!.first) + 1
                                },
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    OutlinedTextField(
                        value = minTravelers.toString(),
                        onValueChange = { minTravelers = it.toIntOrNull() ?: 10 },
                        label = { Text("Min Travelers") },
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                        modifier = Modifier.weight(1f),
                    )
                    OutlinedTextField(
                        value = maxTravelers.toString(),
                        onValueChange = { maxTravelers = it.toIntOrNull() ?: 30 },
                        label = { Text("Max Travelers") },
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                        modifier = Modifier.weight(1f),
                    )
                }

                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                datePickerShown = true
                            }.padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text =
                            if (tripDate != null) {
                                "${DateHelper.convertMillisToDate(tripDate!!.first)} - ${
                                    DateHelper.convertMillisToDate(tripDate!!.second)
                                }"
                            } else {
                                "Start date - End date"
                            },
                    )

                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        text = "Minimum trip length: ${cities.sumOf { it.daysSpent }} days",
                    )
                }

                if (datePickerShown) {
                    DateRangePickerModal({
                        tripDate = it
                    }) {
                        datePickerShown = false
                    }
                }

                Column {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "Pick transportation type:",
                    )

                    TransportationType.entries.forEach {
                        Row(
                            modifier =
                                modifier
                                    .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = transportationType == it,
                                onClick = { transportationType = it },
                            )
                            Text(
                                text = it.name,
                            )
                        }
                    }
                }
            }

            CustomButton(
                text = "Save changes",
                onClick = {
                    onSaveChanges(
                        Trip(
                            id = tripId,
                            tripName = name,
                            cities = cities,
                            minTravelers = minTravelers,
                            maxTravelers = maxTravelers,
                            transportation = transportationType,
                            tripStartDate = tripDate?.first,
                            tripEndDate = tripDate?.second,
                        ),
                    )
                },
            )
            CustomButton(
                text = "Create trip",
                enabled = createEnabled,
                onClick = {
                    onCreateTrip(
                        Trip(
                            id = tripId,
                            tripName = name,
                            cities = cities,
                            minTravelers = minTravelers,
                            maxTravelers = maxTravelers,
                            transportation = transportationType,
                            tripStartDate = tripDate?.first,
                            tripEndDate = tripDate?.second,
                        ),
                    )
                },
            )
        }

        showDaysInputDialog?.let { i ->
            DaysCityDialog(
                cityName = cities[i].cityName,
                onDaysEntered = { days ->
                    cities[i] = cities[i].copy(daysSpent = days)
                    showDaysInputDialog = null
                },
                onDismiss = { showDaysInputDialog = null },
            )
        }

        AnimatedVisibility(
            visible = showLoader,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
        ) {
            SubmitLoader(
                lottieRes = R.raw.suitcase_lottie,
                text = "Trip created! Hang on tight while we contact partners",
            )
        }
    }

    LaunchedEffect(tripDate, name, transportationType, cities, minTravelers, maxTravelers) {
        tripLength = cities.sumOf { it.daysSpent }
        if (tripDate != null) {
            val daysBetween = TimeUnit.MILLISECONDS.toDays(tripDate!!.second - tripDate!!.first)
            createEnabled =
                if ((daysBetween.toInt() + 1) != tripLength) {
                    false
                } else {
                    name.isNotEmpty() &&
                        transportationType != null &&
                        cities.isNotEmpty() &&
                        minTravelers > 0 &&
                        maxTravelers >= minTravelers &&
                        tripDate != null
                }
        } else {
            createEnabled = name.isNotEmpty() &&
                transportationType != null &&
                cities.isNotEmpty() &&
                minTravelers > 0 &&
                maxTravelers >= minTravelers &&
                tripDate != null
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripCreationFormPreview() {
    TripCreationForm(
        tripWrapper =
            TripWrapper(
                null,
                listOf(),
            ),
        tripId = 1,
        onCreateTrip = {},
        onSaveChanges = {},
        onBackPressed = {},
    )
}
