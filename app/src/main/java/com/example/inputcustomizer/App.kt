package com.example.inputcustomizer

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.inputcustomizer.data.KeyboardsSerializer

private const val KEYBOARDS_NAME = "keyboards"
private const val DATA_STORE_FILE_NAME = "keyboards.pb"
private const val SORT_ORDER_KEY = "sort_order"

val Context.keyboardsStore: DataStore<Keyboards> by dataStore(
  fileName = DATA_STORE_FILE_NAME,
  serializer = KeyboardsSerializer
)

class App : Application() {}

