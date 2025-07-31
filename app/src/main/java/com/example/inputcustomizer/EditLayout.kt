package com.example.inputcustomizer

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import com.example.inputcustomizer.components.Dialog
import com.example.inputcustomizer.components.Heading
import com.example.inputcustomizer.components.Select
import com.example.inputcustomizer.components.Subheading
import kotlinx.coroutines.launch

@Composable
fun EditLayout(
  navController: NavController,
  modifier: Modifier = Modifier,
  viewModel: KeyboardsViewModel = viewModel(),
  keyboardId: Int,
  layoutId: Int,
) {
  val scope = rememberCoroutineScope()

  val keyboards by viewModel.keyboardsFlow.collectAsState(initial = emptyList())
  Log.d("EditKeyboard", keyboards.toString())
  val keyboard =
    keyboards.find { it.id == keyboardId } ?: Keyboard.getDefaultInstance()
  val layout = keyboard.layoutsList.find { it.id == layoutId }
    ?: Layout.getDefaultInstance()

  var keyEditRow: Int? by remember { mutableStateOf(null) }
  var keyEditKey: Int? by remember { mutableStateOf(null) }
  var keyEdit: Key.Builder? by remember { mutableStateOf(null) }
  val openEditKey = { rowIndex: Int, keyIndex: Int, key: Key ->
    keyEditRow = rowIndex
    keyEditKey = keyIndex
    keyEdit = key.toBuilder()
  }
  val dismissEditKey = {
    keyEditRow = null
    keyEditKey = null
    keyEdit = null
  }

  Scaffold(
    modifier
      .fillMaxSize()
      .padding(dimensionResource(R.dimen.global_margin))
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      Heading("Edit: " + (layout.name ?: "Unknown Layout"))
      Column {
        layout.rowsList.mapIndexed { rowIndex, row ->
          Row {
            row.keysList.mapIndexed { keyIndex, key ->
              Button(onClick = {
                openEditKey(rowIndex, keyIndex, key)
              }) {
                Text(text = key.text.toString())
              }
            }
            Button(onClick = {
              scope.launch {
                viewModel.addKey(
                  keyboardId,
                  layoutId,
                  rowIndex
                )
              }
            }) {
              Text("+")
            }
          }
        }
        Button(onClick = {
          scope.launch {
            viewModel.addRow(keyboardId, layoutId)
          }
        }, modifier = Modifier.fillMaxWidth()) {
          Text("Add Row...")
        }
      }
    }

    when {
      keyEditRow != null && keyEditKey != null && keyEdit != null -> {
        Dialog({
          dismissEditKey()
        }) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(dimensionResource(R.dimen.global_margin)),
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = dimensionResource(R.dimen.standard_gap))
                .verticalScroll(rememberScrollState()),
              verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.standard_gap))
            ) {
              KeyAction(
                "Tap",
                keyEdit!!.function,
                {
                  keyEdit = keyEdit!!.clone().setFunction(it)
                },
                keyEdit!!.text,
                {
                  keyEdit = keyEdit!!.clone().setText(it)
                })
              KeyAction(
                "Shift",
                keyEdit!!.shiftFunction,
                {
                  keyEdit = keyEdit!!.clone().setShiftFunction(it)
                },
                keyEdit!!.shiftText,
                {
                  keyEdit = keyEdit!!.clone().setShiftText(it)
                })
              KeyAction(
                "Long Press",
                keyEdit!!.longpressFunction,
                {
                  keyEdit = keyEdit!!.clone().setLongpressFunction(it)
                },
                keyEdit!!.longpressText,
                {
                  keyEdit = keyEdit!!.clone().setLongpressText(it)
                })
              KeyAction(
                "Double Tap",
                keyEdit!!.doubletapFunction,
                {
                  keyEdit = keyEdit!!.clone().setDoubletapFunction(it)
                },
                keyEdit!!.doubletapText,
                {
                  keyEdit = keyEdit!!.clone().setDoubletapText(it)
                })
            }
            Button(
              onClick = {
                //TODO: save key
                scope.launch {
                  viewModel.editKey(
                    keyboardId,
                    layoutId,
                    keyEditRow!!,
                    keyEditKey!!,
                    keyEdit!!.build()
                  )
                  dismissEditKey()
                }
              },
              modifier = Modifier
                .fillMaxWidth()
                .padding(
                  top = dimensionResource(R.dimen.standard_gap),
                  bottom = dimensionResource(R.dimen.standard_gap)
                )
            ) {
              Text("Done")
            }
          }
        }
      }
    }
  }
}

@Composable
fun KeyAction(
  action: String,
  function: KeyFunction,
  setFunction: (KeyFunction) -> Unit,
  text: String,
  setText: (String) -> Unit,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.standard_gap))
  ) {
    Subheading(action)
    Select(
      options = KeyFunction.entries.map {
        Pair(
          it.name,
          it
        )
      },
      value = function,
      onChange = {
        setFunction(it)
      },
      modifier = Modifier.fillMaxWidth(),
      label = { Text("Function") }
    )
    OutlinedTextField(
      value = text,
      onValueChange = {
        setText(it)
      },
      modifier = Modifier.fillMaxWidth(),
      label = { Text("Text") }
    )
  }
}
