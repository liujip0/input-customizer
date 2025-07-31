package com.example.inputcustomizer.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Select(
  options: List<Pair<String, T>>,
  value: T,
  onChange: (T) -> Unit,
  modifier: Modifier = Modifier,
  label: @Composable (() -> Unit)?,
) {
  var expanded by remember { mutableStateOf(false) }

  ExposedDropdownMenuBox(
    expanded = expanded,
    onExpandedChange = { expanded = !expanded }) {
    OutlinedTextField(
      modifier = modifier.menuAnchor(
        MenuAnchorType.PrimaryEditable,
        enabled = true
      ),
      readOnly = true,
      value = options.find { it.second == value }?.first ?: "",
      onValueChange = {},
      label = { label?.invoke() },
      trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
      colors = ExposedDropdownMenuDefaults.textFieldColors(),
    )
    ExposedDropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false }) {
      options.forEach { selectionOption ->
        DropdownMenuItem(
          text = { Text(selectionOption.first) },
          onClick = {
            onChange(selectionOption.second)
            expanded = false
          },
          contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
        )
      }
    }
  }
}
