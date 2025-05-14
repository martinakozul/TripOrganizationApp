package com.camunda.triporganization.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.ui.components.CustomCheckbox

@Composable
fun AddActivityDialog(
    tripActivity: TripActivity?,
    onActivityCreated: (TripActivity) -> Unit,
    onDismiss: () -> Unit
) {
    var activityName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf<Double?>(tripActivity?.price) }
    var isIncluded by remember { mutableStateOf(tripActivity?.isIncluded == true) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add activity") },
        text = {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = activityName,
                        onValueChange = { activityName = it },
                        label = { Text("Activity Name") },
                        singleLine = true,
                        modifier = Modifier.weight(2f)
                    )
                    OutlinedTextField(
                        value = if (price != null) price.toString() else "",
                        onValueChange = { newPrice ->
                            price = newPrice.toDoubleOrNull()
                        },
                        label = { Text("Cost €") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                CustomCheckbox(
                    label = "Is included in price?",
                    isChecked = isIncluded,
                    onCheckedChanged = { isIncluded = !isIncluded }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (activityName.isEmpty().not()) {
                    onActivityCreated(
                        TripActivity(
                            name = activityName,
                            isIncluded = isIncluded,
                            price = price ?: 0.0
                        )
                    )
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
private fun AddActivityDialogPreview() {
    AddActivityDialog(
        tripActivity = null,
        onActivityCreated = {},
        onDismiss = {}
    )
}

data class TripActivity(
    val name: String,
    val isIncluded: Boolean,
    val price: Double
)