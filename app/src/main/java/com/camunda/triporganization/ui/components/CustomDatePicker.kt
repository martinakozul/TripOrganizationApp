package com.camunda.triporganization.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long, Long>) -> Unit,
    onDismiss: () -> Unit,
) {
    val todayMillis =
        remember {
            LocalDate
                .now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        }
    val dateRangePickerState =
        rememberDateRangePickerState(
            initialSelectedStartDateMillis = null,
            initialSelectedEndDateMillis = null,
        )
    val isValidSelection by remember(
        dateRangePickerState.selectedStartDateMillis,
        dateRangePickerState.selectedEndDateMillis,
        todayMillis,
    ) {
        derivedStateOf {
            val start = dateRangePickerState.selectedStartDateMillis
            val end = dateRangePickerState.selectedEndDateMillis
            start != null && end != null && start >= todayMillis && end >= todayMillis
        }
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                enabled =
                    dateRangePickerState.selectedStartDateMillis != null &&
                        dateRangePickerState.selectedEndDateMillis != null &&
                        isValidSelection,
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis!!,
                            dateRangePickerState.selectedEndDateMillis!!,
                        ),
                    )
                    onDismiss()
                },
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Select date range",
                )
            },
            showModeToggle = false,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(500.dp),
        )
    }
}

@Preview
@Composable
private fun DateRangePickerModalPreview() {
    DateRangePickerModal({}) { }
}
