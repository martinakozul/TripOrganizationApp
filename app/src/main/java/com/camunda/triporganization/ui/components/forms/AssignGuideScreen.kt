package com.camunda.triporganization.ui.components.forms

import CustomDropdownMenu
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import com.camunda.triporganization.ui.components.CustomButton
import com.camunda.triporganization.ui.components.CustomTopBar
import com.camunda.triporganization.ui.components.SubmitLoader
import com.camunda.triporganization.ui.theme.Colors.primaryContainer

@Composable
fun AssignGuideScreen(
    guideList: List<String>,
    assignGuide: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    var guide by remember { mutableStateOf<String?>(null) }

    var showLoader by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Assign a guide", onBackPressed = onBackPressed)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomDropdownMenu(
                label = guide ?: "Pick a guide",
                menuItemData = guideList,
                onItemSelected = {
                    guide = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                    .background(
                        primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            )

            CustomButton(
                text = "Assign guide",
                enabled = guide != null,
                onClick = {
                    showLoader = true
                    assignGuide(guide ?: "")
                }
            )
        }

        AnimatedVisibility(
            visible = showLoader,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            SubmitLoader(
                lottieRes = R.raw.suitcase_lottie,
                text = "Guide for the trip assigned. Hang on tight, partner offers will be coming in soon."
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AssignGuideScreenPreview() {
    AssignGuideScreen(
        guideList = listOf("Ana", "Petar", "Marin"),
        assignGuide = {},
        onBackPressed = {}
    )
}