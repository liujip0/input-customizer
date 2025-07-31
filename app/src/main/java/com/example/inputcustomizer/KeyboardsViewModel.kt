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

  suspend fun getKeyboards(): Keyboards? {
    return getKeyboardsFlow(appApplication).firstOrNull()
  }

  suspend fun editKeyboards(newKeyboards: Keyboards) {
    getKeyboardsFlow(appApplication).firstOrNull()?.let { currentKeyboards ->
      appApplication.applicationContext.keyboardsStore.updateData { newKeyboards }
    }
  }

  suspend fun addKeyboard(keyboardName: String): Int? {
    val currentKeyboards = getKeyboards() ?: return null
    val keyboardIds = currentKeyboards.keyboardsList.map { it.id }
    var newKeyboardId = 0
    keyboardIds.let { it ->
      if (!it.isEmpty()) {
        newKeyboardId = max(it) + 1
      }
    }
    editKeyboards(
      currentKeyboards.toBuilder().addKeyboards(
        Keyboard.newBuilder().setName(keyboardName).setId(newKeyboardId)
      ).build()
    )
    return newKeyboardId
  }

  suspend fun getKeyboard(keyboardId: Int): Keyboard? {
    return keyboardsFlow.firstOrNull()?.find { it.id == keyboardId }
  }

  suspend fun editKeyboard(keyboardId: Int, keyboard: Keyboard) {
    val currentKeyboards = getKeyboards()
    if (currentKeyboards != null) {
      val keyboardIndex =
        currentKeyboards.keyboardsList.indexOfFirst { it.id == keyboardId }
      editKeyboards(
        currentKeyboards.toBuilder().setKeyboards(keyboardIndex, keyboard)
          .build()
      )
    }
  }

  suspend fun addLayout(keyboardId: Int, layoutName: String): Int? {
    val currentKeyboard = getKeyboard(keyboardId) ?: return null
    val layoutIds = currentKeyboard.layoutsList.map { it.id }
    var newLayoutId = 0
    if (!layoutIds.isEmpty()) {
      newLayoutId = max(layoutIds) + 1
    }
    editKeyboard(
      keyboardId,
      currentKeyboard.toBuilder()
        .addLayouts(Layout.newBuilder().setName(layoutName).setId(newLayoutId))
        .build()
    )
    return newLayoutId
  }

  suspend fun getLayout(keyboardId: Int, layoutId: Int): Layout? {
    return getKeyboard(keyboardId)?.layoutsList?.find { it.id == layoutId }
  }

  suspend fun editLayout(keyboardId: Int, layoutId: Int, layout: Layout) {
    val currentKeyboard = getKeyboard(keyboardId) ?: return
    val layoutIndex =
      currentKeyboard.layoutsList.indexOfFirst { it.id == layoutId }
    editKeyboard(
      keyboardId,
      currentKeyboard.toBuilder().setLayouts(layoutIndex, layout).build()
    )
  }

  suspend fun addRow(keyboardId: Int, layoutId: Int) {
    val currentLayout = getLayout(keyboardId, layoutId) ?: return
    editLayout(
      keyboardId,
      layoutId,
      currentLayout.toBuilder().addRows(Row.newBuilder()).build()
    )
  }

  suspend fun getRow(keyboardId: Int, layoutId: Int, rowIndex: Int): Row? {
    return getLayout(keyboardId, layoutId)?.getRows(rowIndex)
  }

  suspend fun editRow(keyboardId: Int, layoutId: Int, rowIndex: Int, row: Row) {
    val currentLayout = getLayout(keyboardId, layoutId) ?: return
    editLayout(
      keyboardId,
      layoutId,
      currentLayout.toBuilder().setRows(rowIndex, row).build()
    )
  }

  suspend fun addKey(keyboardId: Int, layoutId: Int, rowIndex: Int) {
    val currentRow = getRow(keyboardId, layoutId, rowIndex) ?: return
    editRow(
      keyboardId,
      layoutId,
      rowIndex,
      currentRow.toBuilder().addKeys(Key.newBuilder()).build()
    )
  }

  suspend fun getKey(
    keyboardId: Int,
    layoutId: Int,
    rowIndex: Int,
    keyIndex: Int,
  ): Key? {
    return getRow(keyboardId, layoutId, rowIndex)?.getKeys(keyIndex)
  }

  suspend fun editKey(
    keyboardId: Int,
    layoutId: Int,
    rowIndex: Int,
    keyIndex: Int,
    key: Key,
  ) {
    val currentRow = getRow(keyboardId, layoutId, rowIndex) ?: return
    editRow(
      keyboardId,
      layoutId,
      rowIndex,
      currentRow.toBuilder().setKeys(keyIndex, key).build()
    )
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
