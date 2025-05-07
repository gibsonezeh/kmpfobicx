package com.gibson.fobicx

import android.os.Build

class AndroidPlatform : Platform {
  override val name: String = "Andriod ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
