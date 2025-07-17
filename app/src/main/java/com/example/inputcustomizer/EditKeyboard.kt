package com.example.inputcustomizer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inputcustomizer.components.Heading
import com.example.inputcustomizer.components.Subheading

@Composable
fun EditKeyboard(
  navController: NavController,
  modifier: Modifier = Modifier,
  viewModel: KeyboardsViewModel = viewModel(),
) {
  val keyboard by remember { mutableStateOf(Keyboard.getDefaultInstance()) }
  var openNewLayoutDialog by remember { mutableStateOf(false) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .padding(dimensionResource(R.dimen.global_margin))
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .padding(innerPadding)

    ) {
      Heading(
        "New Keyboard",
      )
      Column {
        Subheading("Layouts")
        if (keyboard.layoutsList.isEmpty()) {
          Text("No layouts found")
        } else {
          keyboard.layoutsList.forEach { layout ->
            Button(
              onClick = {},
              modifier = Modifier.fillMaxWidth()
            ) { Text(layout.name) }
          }
        }
        Button(
          onClick = { openNewLayoutDialog = true },
          modifier = Modifier.fillMaxWidth()
        ) { Text("Add Layout...") }
      }
    }

    when {
      openNewLayoutDialog -> {
        NewLayoutDialog(
          onDismissRequest = { openNewLayoutDialog = false },
          onDone = { layoutName -> })
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewLayoutDialog(
  onDismissRequest: () -> Unit,
  onDone: (String) -> Unit,
) {
  val bottomSheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true
  )

  var layoutName by remember { mutableStateOf("") }

  ModalBottomSheet(
    onDismissRequest = onDismissRequest,
    sheetState = bottomSheetState
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.global_margin))
    ) {
      Subheading("Create new layout")
      OutlinedTextField(
        value = layoutName,
        onValueChange = { layoutName = it },
        label = { Text("Layout Name") },
        modifier = Modifier.fillMaxWidth()
      )
      Button(
        onClick = { onDone(layoutName) },
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
