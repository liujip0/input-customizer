package com.example.inputcustomizer

import EditLayoutRoute
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inputcustomizer.components.Heading
import com.example.inputcustomizer.components.NewItemDialog
import com.example.inputcustomizer.components.Subheading
import kotlinx.coroutines.launch

@Composable
fun EditKeyboard(
  navController: NavController,
  modifier: Modifier = Modifier,
  viewModel: KeyboardsViewModel = viewModel(),
  keyboardId: Int,
) {
  val scope = rememberCoroutineScope()

  val keyboards by viewModel.keyboardsFlow.collectAsState(initial = emptyList())
  Log.d("EditKeyboard", keyboards.toString())
  val keyboard =
    keyboards.find { it.id == keyboardId } ?: Keyboard.getDefaultInstance()
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
        "Edit: " + (keyboard.name
          ?: "Unknown Keyboard"),
      )
      Column {
        Subheading("Layouts")
        if (keyboard.layoutsList.isEmpty()) {
          Text("No layouts found")
        } else {
          keyboard.layoutsList.forEach { layout ->
            Button(
              onClick = {
                navController.navigate(
                  EditLayoutRoute(
                    keyboardId,
                    layout.id
                  )
                )
              },
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
        NewItemDialog(
          onDismissRequest = { openNewLayoutDialog = false },
          onDone = { layoutName ->
            scope.launch {
              val layoutId = viewModel.addLayout(
                keyboardId = keyboardId,
                layoutName = layoutName
              )
              openNewLayoutDialog = false
              if (layoutId != null) {
                navController.navigate(
                  EditLayoutRoute(
                    keyboardId,
                    layoutId
                  )
                )
              }
            }
          },
          title = "Create new layout",
          nameLabel = "Layout name"
        )
      }
    }
  }
}

