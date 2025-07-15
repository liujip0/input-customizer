package com.example.inputcustomizer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomKeyboard(onPress: (String) -> Unit) {
  val rows = listOf(
    listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
    listOf("A", "S", "D", "F", "G", "H", "J", "K", "L"),
    listOf("Shift", "Z", "X", "C", "V", "B", "N", "M", "DEL"),
    listOf("Space", "Enter")
  )

  Column(
    modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFEFEFEF))
        .padding(4.dp)
  ) {
    for (row in rows) {
      Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        for (key in row) {
          Key(
            label = key,
            modifier = Modifier.padding(2.dp)
          ) {

          }
        }
      }
    }
  }
}

@Composable
fun Key(label: String, modifier: Modifier, onClick: () -> Unit) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
        .background(
            color = Color.White,
            RoundedCornerShape(8.dp)
        )
        .size(width = 36.dp, height = 44.dp)
        .clickable { onClick() }
  ) {
    Text(text = label, fontSize = 18.sp, color = Color.Black)
  }
}
