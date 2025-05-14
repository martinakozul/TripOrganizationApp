package com.camunda.triporganization.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.R
import com.camunda.triporganization.model.TransportationType
import com.camunda.triporganization.model.Trip

@Composable
fun TripTile(trip: Trip, modifier: Modifier = Modifier) {
    val transportIconId = when (trip.transportation) {
        TransportationType.BUS -> R.drawable.ic_bus
        TransportationType.PLANE -> R.drawable.ic_plane
        else -> R.drawable.ic_bus
    }
    Row(
        modifier = modifier

            .shadow(2.dp, shape = RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

//        Image(
//            painter = painterResource(FlagHelper.getFlagForCountry(trip.country)),
//            contentDescription = null,
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier
//                .size(width = 48.dp, height = 28.dp)
//                .clip(RoundedCornerShape(4.dp))
//        )

        Text(
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            text = trip.tripName ?: "",
            style = MaterialTheme.typography.bodyLarge
        )
//        Text(
//            modifier = Modifier.width(48.dp),
//            text = DateHelper.convertMillisToDate(trip.startDateTimestamp),
//            style = MaterialTheme.typography.bodyMedium,
//            textAlign = TextAlign.Center
//        )
//        Text(
//            modifier = Modifier.width(48.dp),
//            text = DateHelper.convertMillisToDate(trip.endDateTimestamp),
//            style = MaterialTheme.typography.bodyMedium,
//            textAlign = TextAlign.Center
//        )
        Text(
            modifier = Modifier.width(48.dp),
            text = trip.price.toString(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
//        Icon(
//            modifier = Modifier.size(16.dp),
//            painter = painterResource(transportIconId),
//            contentDescription = null
//        )

    }
}
