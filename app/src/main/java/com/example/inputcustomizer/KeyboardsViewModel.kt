package com.example.inputcustomizer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class KeyboardsViewModel(application: Application) :
  AndroidViewModel(application) {
  private val appApplication: Application = application

  val keyboardsFlow: Flow<Keyboards> =
    appApplication.applicationContext.keyboardsStore.data.catch { exception ->
      if (exception is IOException) {
        Log.e("KeyboardsViewModel", "Error reading keyboards.", exception)
        emit(Keyboards.getDefaultInstance())
      } else {
        throw exception
      }
    }
}
