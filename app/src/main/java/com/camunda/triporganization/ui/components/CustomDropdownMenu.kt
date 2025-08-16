import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.ui.theme.AppTypography

@Composable
fun CustomDropdownMenu(
    label: String,
    menuItemData: List<String>,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = modifier.clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = AppTypography.labelLarge,
                text = label,
            )
            Icon(
                modifier =
                    Modifier
                        .padding(start = 4.dp)
                        .size(16.dp),
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            menuItemData.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        expanded = !expanded
                        onItemSelected(option)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomDropdownMenuPreview() {
    CustomDropdownMenu(
        label = "Add city",
        onItemSelected = {},
        menuItemData = List(5) { "Option ${it + 1}" },
        modifier = Modifier.padding(16.dp),
    )
}
