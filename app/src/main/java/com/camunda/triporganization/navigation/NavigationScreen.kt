package com.camunda.triporganization.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.camunda.triporganization.ui.components.TripListAction
import com.camunda.triporganization.ui.components.TripTilesList
import com.camunda.triporganization.ui.components.forms.AssignGuideScreen
import com.camunda.triporganization.ui.components.forms.LogInForm
import com.camunda.triporganization.ui.components.forms.PartnerOfferReviewScreen
import com.camunda.triporganization.ui.components.forms.TripCreationForm
import com.camunda.triporganization.ui.components.forms.TripPlanForm
import com.camunda.triporganization.ui.components.forms.TripPlanReviewForm
import com.camunda.triporganization.viewmodel.AssignGuideViewModel
import com.camunda.triporganization.viewmodel.ItineraryReviewViewModel
import com.camunda.triporganization.viewmodel.LogInViewModel
import com.camunda.triporganization.viewmodel.PartnerOfferReviewViewModel
import com.camunda.triporganization.viewmodel.TripCreateViewModel
import com.camunda.triporganization.viewmodel.TripItineraryViewModel
import com.camunda.triporganization.viewmodel.TripTilesListViewModel

sealed class NavigationScreen(
    val route: String,
) {
    object LogIn : NavigationScreen("log_in")

    object TripList : NavigationScreen("trip_list")

    object TripCreationForm : NavigationScreen("trip_creation_form")

    object TripItineraryForm : NavigationScreen("trip_itinerary_form")

    object TripItineraryReview : NavigationScreen("trip_itinerary_review")

    object AssignGuideForm : NavigationScreen("assign_guide_form")

    object PartnerOffersReview : NavigationScreen("partner_offers_review")
}

@Composable
fun LogIn(onNavigateToTripList: () -> Unit) {
    val viewModel: LogInViewModel = viewModel()
    val roleState = viewModel.roleState.collectAsState()

    if (roleState.value) {
        onNavigateToTripList()
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LogInForm(onLoggedIn = { viewModel.logIn(it) })
    }
}

@Composable
fun TripList(
    onLoggedOut: () -> Unit,
    onNavigateToCreateForm: (Long) -> Unit,
    onNavigateToReviewOffers: (Long) -> Unit,
    onNavigateToAssignGuideForm: (Long) -> Unit,
    onNavigateToTripItineraryForm: (Long) -> Unit,
    onNavigateToTripItineraryReviewForm: (Long) -> Unit,
) {
    val viewModel: TripTilesListViewModel = viewModel()

    val startedProcesses by viewModel.processes.collectAsState()
    val taskTypes by viewModel.options.collectAsState()
    val selectedType by viewModel.selectedType.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer =
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.fetchTasks()
                }
            }

        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    TripTilesList(
        startedProcesses = startedProcesses ?: listOf(),
        taskTypes = taskTypes,
        selectedType = selectedType,
        onLoggedOut = onLoggedOut,
        onAction = { action ->
            when (action) {
                is TripListAction.AssignGuideAction -> {
                    onNavigateToAssignGuideForm(action.processId)
                }

                is TripListAction.ChangeTaskTypeAction -> {
                    viewModel.changeTaskType(action.type)
                }

                is TripListAction.CreateFormAction -> {
                    if (action.processId == null) {
                        viewModel.createTrip {
                            onNavigateToCreateForm(it)
                        }
                    } else {
                        onNavigateToCreateForm(action.processId)
                    }
                }

                is TripListAction.ReviewOffersAction -> {
                    onNavigateToReviewOffers(action.processId)
                }

                is TripListAction.TripItineraryAction -> {
                    onNavigateToTripItineraryForm(action.processId)
                }

                is TripListAction.TripItineraryReviewAction -> {
                    onNavigateToTripItineraryReviewForm(action.processId)
                }
            }
        },
    )
}

@Composable
fun TripCreationForm(
    onNavigateToAssignGuideForm: (Long) -> Unit,
    onBackPressed: () -> Unit,
    tripId: Long,
) {
    val viewModel: TripCreateViewModel = viewModel()
    viewModel.fetchTripDetails(tripId)
    val trip = viewModel.tripDetails.collectAsState()
    TripCreationForm(
        tripWrapper = trip.value,
        tripId = tripId,
        onSaveChanges = {
            viewModel.saveTripDetails(it)
        },
        onCreateTrip = {
            viewModel.createTrip(it)
        },
        onBackPressed = onBackPressed,
        onNavigateToAssignGuideForm = {
            onNavigateToAssignGuideForm.invoke(tripId)
        },
    )
}

@Composable
fun TripItineraryForm(
    tripId: Long,
    onBackPressed: () -> Unit,
) {
    val viewModel: TripItineraryViewModel = viewModel()
    viewModel.fetchTripData(tripId)
    val tripItinerary = viewModel.tripInformation.collectAsState()

    TripPlanForm(
        trip = tripItinerary.value,
        onSubmitClicked = { plan ->
            viewModel.submitTripItinerary(tripId, plan)
        },
        onBackPressed = onBackPressed,
    )
}

@Composable
fun TripItineraryReview(
    tripId: Long,
    onBackPressed: () -> Unit,
) {
    val viewModel: ItineraryReviewViewModel = viewModel()
    val trip = viewModel.tripItinerary.collectAsState()

    viewModel.fetchTripItinerary(tripId)

    TripPlanReviewForm(
        trip.value,
        onPublishClicked = { viewModel.submitReview(tripId, it, null) },
        onRejectClicked = { viewModel.submitReview(tripId, null, it) },
        onBackPressed = onBackPressed,
    )
}

@Composable
fun AssignGuideForm(
    tripId: Long,
    onBackPressed: () -> Unit,
) {
    val viewModel: AssignGuideViewModel = viewModel()

    viewModel.getOffers(tripId)

    val availableGuides = viewModel.availableGuides.collectAsState()

    AssignGuideScreen(
        guideList = availableGuides.value,
        assignGuide = { guide ->
            viewModel.assignTourGuide(tripId, guide)
        },
        onBackPressed = onBackPressed,
    )
}

@Composable
fun PartnerOffersReview(
    tripId: Long,
    onBackPressed: () -> Unit,
) {
    val viewModel: PartnerOfferReviewViewModel = viewModel()
    val offers = viewModel.offers.collectAsState()

    viewModel.getOffers(tripId)

    Scaffold { paddingValues ->
        PartnerOfferReviewScreen(
            trip = offers.value?.trip,
            transportOffers = offers.value?.groupedPartnerOffers?.transport ?: emptyMap(),
            accommodationOffers = offers.value?.groupedPartnerOffers?.accommodation ?: emptyMap(),
            onOffersAccepted = { transportOffer, accommodationOffer ->
                viewModel.acceptOffers(tripId, transportOffer, accommodationOffer)
            },
            onOffersRejected = {
                viewModel.rejectAllOffers(tripId)
            },
            onBackPressed = onBackPressed,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
        )
    }
}
