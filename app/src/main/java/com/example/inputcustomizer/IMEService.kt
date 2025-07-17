package com.example.inputcustomizer

import android.annotation.SuppressLint
import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class IMEService : InputMethodService(), View.OnClickListener {
  private val TAG = "IMEService"

  lateinit var inputConnection: InputConnection

  init {
    Log.d(TAG, "init")
  }

  override fun onCreate() {
    super.onCreate()
    Log.d(TAG, "onCreate")
  }

  override fun onBindInput() {
    Log.d(TAG, "onBindInput")
    super.onBindInput()
  }

  override fun onInitializeInterface() {
    Log.d(TAG, "onInitializeInterface")
    super.onInitializeInterface()
  }

  override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
    super.onStartInput(attribute, restarting)
    Log.d(TAG, "onStartInput")
  }

  override fun onShowInputRequested(
    flags: Int,
    configChange: Boolean,
  ): Boolean {
    return true
  }

  override fun onEvaluateInputViewShown(): Boolean {
    super.onEvaluateInputViewShown()
    return true
  }

  @SuppressLint("InflateParams")
  override fun onCreateInputView(): View {
    Log.d(TAG, "onCreateInputView")
    val keyboardView: View =
      layoutInflater.inflate(R.layout.key_layout, null)
    ViewCompat.setOnApplyWindowInsetsListener(keyboardView) { view, windowInsets ->
      val insets =
        windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())

      view.updatePadding(bottom = insets.bottom)

      windowInsets
    }

    keyboardView.requestLayout()

    return keyboardView
  }

  override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
    Log.d(TAG, "onStartInputView")
    super.onStartInputView(info, restarting)
    inputConnection = getCurrentInputConnection()
    Log.d(TAG, "inputConnection: $inputConnection")
  }

  override fun onClick(v: View?) {
    TODO("Not yet implemented")
  }

}

