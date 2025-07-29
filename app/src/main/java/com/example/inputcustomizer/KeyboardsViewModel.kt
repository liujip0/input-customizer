package com.example.inputcustomizer

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.Collections.max

class KeyboardsViewModel(application: Application) :
  AndroidViewModel(application) {
  private val appApplication: Application = application

  val keyboardsFlow: Flow<List<Keyboard>>
    get() = getKeyboardListFlow(appApplication)

  suspend fun addKeyboard(keyboardName: String) {
    val keyboardIds = keyboardsFlow.firstOrNull()?.map { it.id }
    var newKeyboardId = 0
    keyboardIds?.let { it ->
      if (!it.isEmpty()) {
        newKeyboardId = max(it) + 1
      }
    }

    getKeyboardsFlow(appApplication).firstOrNull()?.let { currentKeyboards ->
      val newKeyboards = currentKeyboards.toBuilder()
        .addKeyboards(
          Keyboard.newBuilder()
            .setName(keyboardName)
            .setId(
              newKeyboardId
            )
        ).build()
      appApplication.applicationContext.keyboardsStore.updateData { newKeyboards }
    }
  }

  suspend fun addLayoutToKeyboard(keyboardId: Int, layoutName: String) {
    val keyboard =
      keyboardsFlow.firstOrNull()?.find { it.id == keyboardId } ?: return
    val layoutIds = keyboard.layoutsList.map { it.id }
    var newLayoutId = 0
    if (!layoutIds.isEmpty()) {
      newLayoutId = max(layoutIds) + 1
    }
    getKeyboardsFlow(appApplication).firstOrNull()?.let { currentKeyboards ->
      val builder = currentKeyboards.toBuilder()
      val keyboardIndex =
        builder.keyboardsList.indexOfFirst { it.id == keyboardId }
      if (keyboardIndex != -1) {
        val existingKeyboard = builder.getKeyboards(keyboardIndex)
        val updatedKeyboard = existingKeyboard.toBuilder().addLayouts(
          Layout.newBuilder().setName(layoutName).setId(newLayoutId)
        ).build()
        builder.setKeyboards(keyboardIndex, updatedKeyboard)
        appApplication.applicationContext.keyboardsStore.updateData { builder.build() }
      }
    }
  }
}

fun getKeyboardListFlow(application: Application): Flow<List<Keyboard>> {
  return application.applicationContext.keyboardsStore.data.map {
    it.keyboardsList
  }
    .catch { exception ->
      if (exception is IOException) {
        Log.e("KeyboardsViewModel", "Error reading keyboards.", exception)
        emit(emptyList())
      } else {
        throw exception
      }
    }
}

fun getKeyboardsFlow(application: Application): Flow<Keyboards> {
  return application.applicationContext.keyboardsStore.data
    .catch { exception ->
      if (exception is IOException) {
        Log.e("KeyboardsViewModel", "Error reading keyboards.", exception)
        emit(Keyboards.getDefaultInstance())
      } else {
        throw exception
      }
    }
}
