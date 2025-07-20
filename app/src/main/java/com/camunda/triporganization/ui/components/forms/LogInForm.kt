package com.camunda.triporganization.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.camunda.triporganization.R
import com.camunda.triporganization.helper.Auth0Helper
import com.camunda.triporganization.ui.components.CustomButton
import com.camunda.triporganization.ui.theme.Colors.surface

@Composable
fun LogInForm(
    onLoggedIn: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.world_travel_loader))

    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize().background(surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        LottieAnimation(
            speed = 0.5f,
            composition = lottieComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(200.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            text = "Log in",
            onClick = { Auth0Helper.logIn(context, onLoggedIn) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LogInFormPreview() {
    LogInForm({ _ -> })
}
