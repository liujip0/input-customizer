package com.example.inputcustomizer.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.inputcustomizer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(onDismissRequest: () -> Unit, content: @Composable () -> Unit) {
  val bottomSheetState =
    rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismissRequest,
    sheetState = bottomSheetState
  ) {
    content()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewItemDialog(
  onDismissRequest: () -> Unit,
  onDone: (String) -> Unit,
  title: String,
  nameLabel: String,
) {
  var itemName by remember { mutableStateOf("") }

  Dialog(onDismissRequest) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.global_margin))
    ) {
      Subheading(title)
      OutlinedTextField(
        value = itemName,
        onValueChange = { itemName = it },
        label = { Text(nameLabel) },
        modifier = Modifier.fillMaxWidth()
      )
      Button(
        onClick = { onDone(itemName) },
        modifier = Modifier
          .padding(
            top = dimensionResource(R.dimen.standard_gap),
            bottom = dimensionResource(R.dimen.standard_gap)
          )
          .fillMaxWidth()
      ) {
        Text("Done")
      }
    }
  }
}
