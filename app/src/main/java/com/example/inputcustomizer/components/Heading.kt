package com.example.inputcustomizer.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.inputcustomizer.R

@Composable
fun Heading(text: String) {
  Text(
    text,
    fontSize = dimensionResource(R.dimen.heading_font_size).value.sp,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.heading_bottom_margin))
  )
}

@Composable
fun Subheading(text: String) {
  Text(
    text,
    fontSize = dimensionResource(R.dimen.subheading_font_size).value.sp,
    fontWeight = FontWeight.Bold,
    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.subheading_bottom_margin))
  )
}
