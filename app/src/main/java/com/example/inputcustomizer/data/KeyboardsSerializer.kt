package com.example.inputcustomizer.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.inputcustomizer.Keyboards
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object KeyboardsSerializer : Serializer<Keyboards> {
  override val defaultValue: Keyboards = Keyboards.getDefaultInstance()
  override suspend fun readFrom(input: InputStream): Keyboards {
    try {
      return Keyboards.parseFrom(input)
    } catch (exception: InvalidProtocolBufferException) {
      throw CorruptionException("Cannot read proto.", exception)
    }
  }

  override suspend fun writeTo(t: Keyboards, output: OutputStream) {
    t.writeTo(output)
  }
}
