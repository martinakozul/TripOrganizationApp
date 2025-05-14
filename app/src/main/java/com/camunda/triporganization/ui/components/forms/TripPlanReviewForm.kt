package com.camunda.triporganization.ui.components.forms

import androidx.compose.animation.AnimatedVisibility
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
import com.camunda.triporganization.model.TripItinerary
import com.camunda.triporganization.ui.components.CustomCheckbox
import com.camunda.triporganization.ui.components.CustomTopBar

@Composable
fun TripPlanReviewForm(
    tripItinerary: TripItinerary?,
    onPublishClicked: (Double) -> Unit,
    onRejectClicked: (String) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (tripItinerary == null) return

    var note by remember { mutableStateOf("") }
    var price by remember { mutableStateOf<Double?>(null) }
    var isApproved by remember { mutableStateOf(false) }

    var showTripInfo by remember { mutableStateOf(false) }
    var showTripItinerary by remember { mutableStateOf(true) }


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
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .shadow(
                        2.dp,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Trip Information")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(24.dp),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

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
                            text = tripItinerary.tripPlan.joinToString("\n")
                        )
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            text = "Included activities"
                        )
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = tripItinerary.includedActivities.joinToString("\n")
                        )
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            text = "Optional activities"
                        )
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = tripItinerary.extraActivities.joinToString("\n")
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
                        shape = RoundedCornerShape(4.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    enabled = isApproved.not(),
                    value = if (isApproved) "" else note,
                    minLines = 5,
                    onValueChange = { note = it },
                    label = { Text("Note for the guide") },
                    modifier = Modifier.fillMaxWidth()
                )

                CustomCheckbox(
                    onCheckedChanged = {
                        isApproved = !isApproved
                    },
                    isChecked = isApproved,
                    label = "Approve submitted trip plan"
                )

                if (isApproved) {
                    OutlinedTextField(
                        value = (price ?: "").toString(),
                        onValueChange = { price = it.toDoubleOrNull() ?: 0.0 },
                        label = { Text("Price per person") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Button(
                onClick = {
                    if (isApproved) onPublishClicked(price ?: 100.0) else onRejectClicked(note)
                },
                enabled = (!isApproved && note.isNotEmpty()) || (price != null && isApproved)
            ) {
                Text(
                    text = if (isApproved) "Publish trip" else "Send back to guide"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripPlanReviewFormPreview() {
    TripPlanReviewForm(
        onRejectClicked = {},
        onPublishClicked = {},
        tripItinerary = TripItinerary(
            tripPlan = listOf(
                    "Day 1 - Zagreb  \n" +
                    "Meet up at xxh, head to the airport, fly to Rome.  \n" +
                    "Check into accommodation and take an evening stroll near the Colosseum.  \n" +
                    "Dinner at a cozy trattoria in Monti.\n\n",
                    "Day 2 - Ancient Rome  \n" +
                    "Morning visit to the Colosseum and Roman Forum.  \n" +
                    "Lunch near Piazza Venezia.  \n" +
                    "Afternoon visit to Palatine Hill.  \n" +
                    "Sunset walk around Capitoline Hill.  \n" +
                    "Dinner in Trastevere.\n\n",
                    "Day 3 - Vatican City  \n" +
                    "Visit St. Peter’s Basilica early to beat the crowds.  \n" +
                    "Tour the Vatican Museums and Sistine Chapel.  \n" +
                    "Picnic lunch in the Vatican Gardens (if available) or nearby café.  \n" +
                    "Explore Castel Sant’Angelo in the evening.  \n" +
                    "Dinner along the Tiber River.\n\n",
                    "Day 4 - Baroque Rome & Hidden Gems  \n" +
                    "Walk through Piazza Navona and visit the Pantheon.  \n" +
                    "Espresso break at Sant’Eustachio Il Caffè.  \n" +
                    "Explore Campo de' Fiori and shop for souvenirs.  \n" +
                    "Afternoon visit to Villa Borghese & Galleria Borghese.  \n" +
                    "Dinner in the Jewish Ghetto.\n\n",
                    "Day 5 - Farewell Rome  \n" +
                    "Relaxed breakfast near the Spanish Steps.  \n" +
                    "Free time for last-minute exploring or shopping.  \n" +
                    "Lunch, then head to the airport for flight back to Zagreb.\n"),
            includedActivities = listOf("one", "two", "three"),
            extraActivities = listOf(),
            note = "Looks great but fix day 2"
        ),
        onBackPressed = {}
    )
}