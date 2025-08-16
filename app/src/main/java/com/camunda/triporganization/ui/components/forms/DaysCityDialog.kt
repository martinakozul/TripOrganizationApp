package com.camunda.triporganization.ui.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.camunda.triporganization.ui.theme.Colors.onSurface
import com.camunda.triporganization.ui.theme.Colors.primary
import com.camunda.triporganization.ui.theme.Colors.secondary
import com.camunda.triporganization.ui.theme.Colors.surface

@Composable
fun DaysCityDialog(
    cityName: String,
    onDaysEntered: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    var daysInCity by remember { mutableStateOf("") }

    AlertDialog(
        containerColor = surface,
        titleContentColor = onSurface,
        textContentColor = onSurface,
        onDismissRequest = { onDismiss() },
        title = { Text("How many days in $cityName?") },
        text = {
            Column {
                OutlinedTextField(
                    value = daysInCity,
                    onValueChange = {
                        daysInCity = it
                    },
                    label = { Text("Days") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val days = daysInCity.toIntOrNull()
                if (days != null) {
                    onDaysEntered(days)
                }
            }) {
                Text(
                    color = primary,
                    text = "SAVE",
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    color = secondary,
                    text = "DISMISS",
                )
            }
        },
    )
}

@Preview
@Composable
private fun DaysCityDialogPreview() {
    DaysCityDialog(
        cityName = "London",
        onDaysEntered = {},
        onDismiss = {},
    )
}
