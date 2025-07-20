package com.camunda.triporganization.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.ui.theme.Colors.inverseOnSurface
import com.camunda.triporganization.ui.theme.Colors.inverseSurface
import com.camunda.triporganization.ui.theme.Colors.onPrimary
import com.camunda.triporganization.ui.theme.Colors.primary
import java.util.Locale

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    backgroundColor: Color = primary,
    contentColor: Color = onPrimary,
    shape: Shape = RoundedCornerShape(0.dp),
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    height: Int = 64,
) {
    Button(
        onClick = onClick,
        modifier =
            modifier
                .shadow(2.dp)
                .height(height.dp)
                .fillMaxWidth(),
        enabled = enabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = contentColor,
                disabledContainerColor = inverseSurface,
                disabledContentColor = inverseOnSurface,
            ),
        shape = shape,
        elevation = ButtonDefaults.buttonElevation(),
    ) {
        Text(text = text.uppercase(Locale.ROOT), style = textStyle)
    }
}

@Preview
@Composable
private fun CustomButtonPreview() {
    CustomButton(text = "Click me", onClick = {})
}
