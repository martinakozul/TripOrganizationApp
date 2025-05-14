package com.camunda.triporganization.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.model.TripItinerary
import com.camunda.triporganization.ui.components.CustomTopBar

@Composable
fun TripPlanForm(
    tripItinerary: TripItinerary?,
    cities: List<Pair<String, Int>>,
    onSubmitClicked: (TripItinerary) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (cities.isEmpty()) return

    val tripPlan =
        remember { mutableStateListOf(*Array(cities.sumOf { it.second } + cities.size) { "" }) }
    var includedActivities by remember { mutableStateOf<List<TripActivity>>(listOf()) }
    var extraActivities by remember { mutableStateOf<List<TripActivity>>(listOf()) }

    var showAddActivity by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Assign a guide", onBackPressed = onBackPressed)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding).fillMaxSize().padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .shadow(2.dp, shape = RoundedCornerShape(4.dp))
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp)
            ) {

                items(cities.size) { index ->
                    val cityPair = cities[index]
                    (0 until cityPair.second.coerceAtLeast(1)).forEach { i ->
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val tripDay = cities.take(index).sumOf { it.second } + index + i + 1
                            Text(text = "${cityPair.first} (Day $tripDay)")
                            OutlinedTextField(
                                value = tripPlan[tripDay - 1],
                                minLines = 5,
                                onValueChange = { tripPlan[tripDay - 1] = it },
                                label = { Text("Itinerary") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAddActivity = true }
                    ) {
                        Text(text = "Add activity")
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(16.dp),
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }

                item {
                    Text(modifier = Modifier.fillMaxWidth(), text = "Included activities:")
                }

                items(includedActivities) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it.name + "€${it.price}")
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(16.dp),
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(16.dp),
                            imageVector = Icons.Default.Create,
                            contentDescription = null
                        )
                    }
                }

                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Extra activities:"
                    )
                }

                items(extraActivities) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it.name + "€${it.price}")
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(16.dp),
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(16.dp),
                            imageVector = Icons.Default.Create,
                            contentDescription = null
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
                            }
                        )
                    }
                }

                item {
                    Button(
                        enabled = tripPlan.all { it.isNotEmpty() },
                        onClick = {
                            onSubmitClicked(
                                TripItinerary(
                                    tripPlan = tripPlan.mapIndexed {i, plan -> "Day $i\n$plan" },
                                    includedActivities = includedActivities.map { "${it.name} (€${it.price})" },
                                    extraActivities = extraActivities.map { "${it.name} (€${it.price})" },
                                    note = ""
                                )
                            )
                        }
                    ) {
                        Text("Submit itinerary")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripPlanFormPreview() {
    TripPlanForm(
        tripItinerary = null,
        onSubmitClicked = {},
        cities = listOf(Pair("Rome", 0), Pair("Paris", 2), Pair("Barcelona", 4)),
        onBackPressed = {}
    )
}