package com.example.inputcustomizer

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inputcustomizer.components.Heading

@Composable
fun EditLayout(
  navController: NavController,
  modifier: Modifier = Modifier,
  viewModel: KeyboardsViewModel = viewModel(),
  keyboardId: Int,
  layoutId: Int,
) {
  val keyboards by viewModel.keyboardsFlow.collectAsState(initial = emptyList())
  Log.d("EditKeyboard", keyboards.toString())
  val keyboard =
    keyboards.find { it.id == keyboardId } ?: Keyboard.getDefaultInstance()
  val layout = keyboard.layoutsList.find { it.id == layoutId }
    ?: Layout.getDefaultInstance()

  Scaffold(
    modifier
      .fillMaxSize()
      .padding(dimensionResource(R.dimen.global_margin))
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      Heading("Edit: " + (layout.name ?: "Unknown Layout"))
      Column {
        layout.rowsList.forEach { row ->
          Row {
            row.keysList.forEach { key ->
              Text(text = key.keycode.toString())
            }
          }
        }
        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
          Text("Add Row...")
        }
      }
    }
  }
}
