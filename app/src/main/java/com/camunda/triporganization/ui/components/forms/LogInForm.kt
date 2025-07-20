package com.camunda.triporganization.ui.components.forms

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.helper.Auth0Helper
import com.camunda.triporganization.ui.components.CustomButton

@Composable
fun LogInForm(
    onLoggedIn: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    CustomButton(
        text = "Log in",
        modifier = modifier,
        onClick = { Auth0Helper.logIn(context, onLoggedIn) },
    )
}

@Preview(showBackground = true)
@Composable
private fun LogInFormPreview() {
    LogInForm({ _ -> }, modifier = Modifier.padding(16.dp))
}
