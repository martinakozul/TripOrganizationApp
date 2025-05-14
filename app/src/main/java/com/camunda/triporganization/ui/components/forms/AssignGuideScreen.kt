package com.camunda.triporganization.ui.components.forms

import CustomDropdownMenu
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.ui.components.CustomTopBar

@Composable
fun AssignGuideScreen(
    guideList: List<String>,
    assignGuide: (String) -> Unit,
    onBackPressed: () -> Unit
) {
    var guide by remember { mutableStateOf<String?>(null) }

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
                    .shadow(2.dp, shape = RoundedCornerShape(4.dp))
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
            )

            Button(
                enabled = guide != null,
                onClick = {
                    assignGuide(guide ?: "")
                }
            ) {
                Text(
                    text = "Assign guide"
                )
            }
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