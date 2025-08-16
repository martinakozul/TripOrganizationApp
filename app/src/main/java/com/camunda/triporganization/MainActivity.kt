package com.camunda.triporganization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.camunda.triporganization.navigation.AssignGuideForm
import com.camunda.triporganization.navigation.LogIn
import com.camunda.triporganization.navigation.NavigationScreen
import com.camunda.triporganization.navigation.PartnerOffersReview
import com.camunda.triporganization.navigation.TripCreationForm
import com.camunda.triporganization.navigation.TripItineraryForm
import com.camunda.triporganization.navigation.TripItineraryReview
import com.camunda.triporganization.navigation.TripList
import com.camunda.triporganization.ui.theme.TripOrganizationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripOrganizationTheme {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        TripOrganizationScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun TripOrganizationScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavigationScreen.LogIn.route,
    ) {
        composable(NavigationScreen.LogIn.route) {
            LogIn(
                onNavigateToTripList = {
                    navController.navigate(NavigationScreen.TripList.route)
                },
            )
        }

        composable(NavigationScreen.TripList.route) {
            TripList(
                onLoggedOut = { navController.navigateUp() },
                onNavigateToCreateForm = { tripId ->
                    navController.navigate(NavigationScreen.TripCreationForm.route + "/$tripId")
                },
                onNavigateToReviewOffers = { tripId ->
                    navController.navigate(NavigationScreen.PartnerOffersReview.route + "/$tripId")
                },
                onNavigateToAssignGuideForm = { tripId ->
                    navController.navigate(NavigationScreen.AssignGuideForm.route + "/$tripId")
                },
                onNavigateToTripItineraryForm = { tripId ->
                    navController.navigate(NavigationScreen.TripItineraryForm.route + "/$tripId")
                },
                onNavigateToTripItineraryReviewForm = { tripId ->
                    navController.navigate(NavigationScreen.TripItineraryReview.route + "/$tripId")
                },
            )
        }

        composable(NavigationScreen.TripCreationForm.route + "/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")?.toLong() ?: 0L
            TripCreationForm(
                onNavigateToAssignGuideForm = {
                    navController.navigate(NavigationScreen.AssignGuideForm.route + "/$tripId")
                },
                onBackPressed = { navController.navigateUp() },
                tripId = tripId,
            )
        }

        composable(NavigationScreen.PartnerOffersReview.route + "/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")?.toLong() ?: 0L
            PartnerOffersReview(
                tripId = tripId,
                onBackPressed = { navController.navigateUp() },
            )
        }

        composable(NavigationScreen.AssignGuideForm.route + "/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")?.toLong() ?: 0L
            AssignGuideForm(tripId = tripId, onBackPressed = { navController.navigateUp() })
        }

        composable(NavigationScreen.TripItineraryForm.route + "/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")?.toLong() ?: 0L
            TripItineraryForm(tripId = tripId, onBackPressed = { navController.navigateUp() })
        }

        composable(NavigationScreen.TripItineraryReview.route + "/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")?.toLong() ?: 0L
            TripItineraryReview(tripId = tripId, onBackPressed = { navController.navigateUp() })
        }
    }
}
