package com.camunda.triporganization.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.ui.theme.Colors.onPrimary
import com.camunda.triporganization.ui.theme.Colors.primary

@Composable
fun CustomTopBar(
    title: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(primary)
                .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier =
                Modifier
                    .padding(start = 4.dp)
                    .size(24.dp)
                    .clickable {
                        onBackPressed()
                    },
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            tint = onPrimary,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = title,
            color = onPrimary,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
private fun CustomTopBarPreview() {
    CustomTopBar("Title", {})
}
