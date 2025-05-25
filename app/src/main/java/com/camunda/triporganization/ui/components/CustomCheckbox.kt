package com.camunda.triporganization.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CustomCheckbox(
    label: String,
    isChecked: Boolean,
    onCheckedChanged: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {


    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            enabled = enabled,
            checked = isChecked,
            onCheckedChange = {
                onCheckedChanged()
            }
        )

        Text(
            text = label
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomCheckboxPreview() {
    CustomCheckbox(isChecked = false, label = "Lorem ipsum", onCheckedChanged = {})
}

@Composable
fun CustomRadioButton(
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = false,
            onClick = {  }
        )
        Text(
            text = label
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomRadioButtonPreview() {
    CustomRadioButton("Lorem ipsum")
}