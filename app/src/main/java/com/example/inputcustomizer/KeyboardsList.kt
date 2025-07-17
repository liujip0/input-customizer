package com.example.inputcustomizer

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inputcustomizer.components.Heading
import com.example.inputcustomizer.components.Subheading

@Composable
fun KeyboardsList(
  navController: NavController,
  modifier: Modifier = Modifier,
  viewModel: KeyboardsViewModel = viewModel(),
) {
  val keyboards by viewModel.keyboardsFlow.collectAsState(
    initial = Keyboards.getDefaultInstance()
  )

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
        if (keyboards.keyboardsList.isEmpty()) {
          Text("No keyboards found")
        } else {
          keyboards.keyboardsList.forEach {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
              Text(it.name)
            }
          }
        }
        Button(
          onClick = { navController.navigate(Screen.NewKeyboard.route) },
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
}
