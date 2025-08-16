package com.camunda.triporganization.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.helper.AppSingleton
import com.camunda.triporganization.helper.Auth0Helper
import com.camunda.triporganization.model.BasicTaskItem
import com.camunda.triporganization.model.UserRole
import com.camunda.triporganization.ui.theme.Colors.onPrimary
import com.camunda.triporganization.ui.theme.Colors.onPrimaryContainer
import com.camunda.triporganization.ui.theme.Colors.primary
import com.camunda.triporganization.ui.theme.Colors.primaryContainer
import com.camunda.triporganization.ui.theme.Colors.surface

@Composable
fun TripTilesList(
    startedProcesses: List<BasicTaskItem>,
    taskTypes: Set<String>,
    selectedType: String,
    onAction: (TripListAction) -> Unit,
    onLoggedOut: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            Row(
                modifier =
                    Modifier
                        .shadow(2.dp)
                        .background(primary)
                        .fillMaxWidth()
                        .heightIn(min = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    color = onPrimary,
                    textAlign = TextAlign.Center,
                    text = "Home",
                    modifier = Modifier.weight(1f),
                )
                Box(
                    modifier =
                        Modifier
                            .width(1.dp)
                            .height(48.dp)
                            .background(onPrimary),
                )
                Text(
                    color = onPrimary,
                    textAlign = TextAlign.Center,
                    text = "Log Out",
                    modifier =
                        Modifier
                            .weight(1f)
                            .clickable {
                                Auth0Helper.logOut(context, onLoggedOut = onLoggedOut)
                            },
                )
            }
        },
        floatingActionButton = {
            if (AppSingleton.userRole == UserRole.COORDINATOR) {
                Row(
                    modifier =
                        Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .clickable { onAction.invoke(TripListAction.CreateFormAction(null)) }
                            .background(
                                MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(8.dp),
                            ).padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onSecondary,
                        text = "Create a new trip",
                    )
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                modifier
                    .fillMaxHeight()
                    .background(surface)
                    .padding(PaddingValues(bottom = innerPadding.calculateBottomPadding())),
        ) {
            item {
                LazyRow(
                    modifier =
                        Modifier
                            .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(taskTypes.toList()) { type ->
                        Text(
                            modifier =
                                Modifier
                                    .background(
                                        if (selectedType == type) primary else primaryContainer,
                                        shape = RoundedCornerShape(4.dp),
                                    ).padding(8.dp)
                                    .clickable {
                                        onAction.invoke(TripListAction.ChangeTaskTypeAction(type))
                                    },
                            text = type,
                            color = if (selectedType == type) onPrimary else onPrimaryContainer,
                        )
                    }
                }
            }
            items(startedProcesses) { process ->
                Column {
                    Text(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val processKey = process.tripId
                                    when (selectedType) {
                                        "Create trip" ->
                                            onAction.invoke(
                                                TripListAction.CreateFormAction(
                                                    processKey,
                                                ),
                                            )

                                        "Review offers" ->
                                            onAction.invoke(
                                                TripListAction.ReviewOffersAction(
                                                    processKey,
                                                ),
                                            )

                                        "Assign tour guide" ->
                                            onAction.invoke(
                                                TripListAction.AssignGuideAction(
                                                    processKey,
                                                ),
                                            )

                                        "Review trip plan" ->
                                            onAction.invoke(
                                                TripListAction.TripItineraryReviewAction(
                                                    processKey,
                                                ),
                                            )

                                        "Define trip plan" ->
                                            onAction.invoke(
                                                TripListAction.TripItineraryAction(processKey),
                                            )
                                    }
                                }.padding(16.dp),
                        text = (process.tripName) ?: ("Unnamed trip " + process.tripId),
                        color = Color.Black,
                    )

                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .heightIn(1.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TripTilesListPreview() {
    var taskType by remember { mutableStateOf("Review offers") }
    TripTilesList(
        startedProcesses =
            listOf(
                BasicTaskItem(
                    tripId = 1,
                    taskName = "Create trip",
                    tripName = "Kapadokija i Antalija iz Zagreba",
                ),
                BasicTaskItem(
                    tripId = 1,
                    taskName = "Create trip",
                    tripName = "Maldivi iz Zagreba",
                ),
                BasicTaskItem(
                    tripId = 1,
                    taskName = "Create trip",
                    tripName = "Kenija Safari i Plaža",
                ),
                BasicTaskItem(
                    tripId = 1,
                    taskName = "Review offers",
                    tripName = "Meksiko iz Zagreba",
                ),
            ),
        taskTypes =
            setOf(
                "Create trip",
                "Review offers",
                "Assign guide",
                "Review itinerary",
                "Publish trip",
            ),
        selectedType = taskType,
        onLoggedOut = {},
        onAction = { action ->
            when (action) {
                is TripListAction.AssignGuideAction -> {}
                is TripListAction.ChangeTaskTypeAction -> {
                    taskType = action.type
                }

                is TripListAction.CreateFormAction -> {}
                is TripListAction.ReviewOffersAction -> {}
                is TripListAction.TripItineraryAction -> {}
                is TripListAction.TripItineraryReviewAction -> {}
            }
        },
    )
}

sealed class TripListAction {
    class CreateFormAction(
        val processId: Long?,
    ) : TripListAction()

    class ReviewOffersAction(
        val processId: Long,
    ) : TripListAction()

    class AssignGuideAction(
        val processId: Long,
    ) : TripListAction()

    class TripItineraryAction(
        val processId: Long,
    ) : TripListAction()

    class TripItineraryReviewAction(
        val processId: Long,
    ) : TripListAction()

    class ChangeTaskTypeAction(
        val type: String,
    ) : TripListAction()
}
