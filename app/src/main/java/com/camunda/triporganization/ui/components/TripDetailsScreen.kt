package com.camunda.triporganization.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.R
import com.camunda.triporganization.model.Trip
import com.camunda.triporganization.ui.theme.TripOrganizationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(trip: Trip, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val collapsed = scrollBehavior.state.collapsedFraction > 0.5f

    TripOrganizationTheme {
        Scaffold(
            modifier = modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        scrolledContainerColor = MaterialTheme.colorScheme.secondary
                    ),
                    navigationIcon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    title = {
                        Column {
                            Text(
                                text = trip.tripName ?: "",
                                style = if (collapsed) MaterialTheme.typography.titleLarge else MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
//                            Text(
//                                text = DateHelper.formatDuration(trip.startDateTimestamp, trip.endDateTimestamp),
//                                style = if (collapsed) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.onPrimary
//                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                Row(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Apply",
                        color = MaterialTheme.colorScheme.onTertiary,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_apply),
                        contentDescription = "Apply",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = modifier
            ) {
                item {
//                    Text(text = trip.itinerary)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun TripDetailsScreenPreview() {
//    TripDetailsScreen(
//        trip = Trip(
//            id = 1,
//            title = "French adventure",
//            cities = "Paris",
//            itinerary = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sodales mauris et mattis facilisis. Nulla velit purus, consectetur a tristique at, consectetur ut enim. Cras sem enim, maximus nec velit in, interdum eleifend ligula. Vivamus ullamcorper diam eu lorem vehicula, a malesuada dolor dignissim. Vivamus vulputate neque vel nisi eleifend porttitor. Duis et finibus diam. Duis a scelerisque metus. Phasellus nisl felis, porta id quam non, varius tempus massa. Pellentesque condimentum consectetur justo ut auctor. Fusce nulla ipsum, auctor a tincidunt at, commodo vitae elit.\n" +
//                    "\n" +
//                    "Mauris in dui et ipsum convallis euismod vel ut eros. Etiam ac ligula vel lectus euismod congue. Duis suscipit et libero quis lobortis. Pellentesque placerat felis ac gravida porta. Donec pulvinar sem nec ante molestie, sed luctus dolor maximus. Morbi dapibus ex id diam venenatis, in laoreet erat convallis. Quisque quam nulla, sollicitudin a odio id, iaculis hendrerit risus. Nunc sed arcu id orci placerat venenatis. Suspendisse at feugiat urna. Cras posuere vel quam vitae gravida. Mauris dictum non diam nec dapibus. Curabitur mollis porttitor tellus in vehicula. Integer sagittis viverra massa, et lobortis turpis. Ut commodo elementum eros et rutrum. Etiam semper quis arcu at molestie.\n" +
//                    "\n" +
//                    "Ut vel eleifend lectus, dictum pulvinar urna. Mauris eget dictum orci. Maecenas mi sapien, facilisis ut eros vitae, egestas cursus velit. Duis sollicitudin posuere sapien, a facilisis purus dapibus at. Morbi at tortor a odio tincidunt tempus a a diam. Vivamus quis dolor at nisi rhoncus semper. Duis a cursus dolor.\n" +
//                    "\n" +
//                    "In dignissim, orci non scelerisque venenatis, lectus urna scelerisque nunc, non ullamcorper ex arcu semper risus. Mauris consectetur, erat vel posuere pharetra, orci ipsum lacinia ante, vel aliquam leo augue euismod mi. Phasellus vitae maximus magna, nec interdum velit. Cras fringilla elit pellentesque lacus consequat molestie. Phasellus dignissim sed risus vitae viverra. Maecenas aliquet eu nisi sit amet bibendum. Cras et eleifend mauris. Praesent a commodo augue, sed consequat magna. Aenean tempor, erat a venenatis accumsan, mi neque ornare mauris, in auctor libero eros consequat urna. Cras a ornare velit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Vivamus non tincidunt purus, id scelerisque justo. Pellentesque id est risus. Phasellus in ligula justo. Donec justo est, ornare id sagittis nec, vestibulum non nunc.\n" +
//                    "\n" +
//                    "Proin gravida odio ultrices nibh lobortis vehicula. Nullam in placerat orci. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Proin eget eros semper, feugiat quam sed, accumsan nunc. Donec placerat ante ex, et malesuada nunc congue at. Fusce vehicula neque dolor, et vulputate metus consectetur et. Nam facilisis ultrices quam quis auctor. Maecenas et arcu ultrices, vestibulum diam id, tincidunt odio. Sed ultricies nisi suscipit purus laoreet lobortis. ",
//            price = 400,
//            transportationType = TransportationType.BUS,
//            country = "France",
//            startDate = LocalDate.now(),
//            endDate = LocalDate.now()
//        )
//    )
//}