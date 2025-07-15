package com.example.inputcustomizer

import android.inputmethodservice.InputMethodService
import android.view.View
import androidx.compose.ui.platform.ComposeView

class IMEService : InputMethodService() {
  override fun onCreateInputView(): View {
    val composeView = ComposeView(this)
    composeView.setContent {
      CustomKeyboard { key ->
        currentInputConnection.commitText(key, 1)
      }
    }
    return composeView
  }
}
