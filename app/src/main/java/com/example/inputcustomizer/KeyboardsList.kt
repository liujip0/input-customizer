package com.example.inputcustomizer

import EditKeyboardRoute
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
fun KeyboardsList(
  navController: NavController,
  modifier: Modifier = Modifier,
  viewModel: KeyboardsViewModel = viewModel(),
) {
  val keyboards by viewModel.keyboardsFlow.collectAsState(
    initial = emptyList()
  )
  val scope = rememberCoroutineScope()

  var openNewKeyboardDialog by remember { mutableStateOf(false) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .padding(dimensionResource(R.dimen.global_margin)),
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      Heading(
        "Android IMEedit",
      )
      Column {
        Subheading("Keyboards")
        if (keyboards.isEmpty()) {
          Text("No keyboards found")
        } else {
          keyboards.forEach {
            Button(
              onClick = {
                Log.d("KeyboardsList", "keyboard clicked: ${it.name}")
                navController.navigate(EditKeyboardRoute(it.id))
              },
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(it.name)
            }
          }
        }
        Button(
          onClick = { openNewKeyboardDialog = true },
          modifier = Modifier
            .padding(
              top = dimensionResource(R.dimen.standard_gap),
              bottom = dimensionResource(R.dimen.standard_gap)
            )
            .fillMaxWidth()
        ) {
          Text("Add Keyboard...")
        }
      }
    }
  }

  when {
    openNewKeyboardDialog -> {
      NewItemDialog(
        onDismissRequest = { openNewKeyboardDialog = false },
        onDone = { keyboardName ->
          scope.launch {
            val keyboardId = viewModel.addKeyboard(
              keyboardName
            )
            openNewKeyboardDialog = false
            if (keyboardId != null) {
              navController.navigate(EditKeyboardRoute(keyboardId))
            }
          }
        },
        title = "Create new keyboard",
        nameLabel = "Keyboard name"
      )
    }
  }
}
