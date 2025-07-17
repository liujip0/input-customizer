package com.example.inputcustomizer

sealed class Screen(val route: String) {
  object KeyboardList : Screen("keyboardList_screen")
  object NewKeyboard : Screen("newKeyboard_screen")
}
