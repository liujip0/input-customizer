package com.example.inputcustomizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.inputcustomizer.ui.theme.InputCustomizerTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      InputCustomizerTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Greeting(
            name = "Android",
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  val textState = remember { mutableStateOf("") }
  Column{
    Text(
      text = "Hello $name!",
      modifier = modifier
    )
    TextField(
      value = textState.value,
      onValueChange = { textState.value = it }
    )
  }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  InputCustomizerTheme {
    Greeting("Android")
  }
}
