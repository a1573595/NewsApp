package com.a1573595.newsapp.common

import android.os.Build
import android.util.Base64 as AndroidBase64
import java.util.Base64 as JavaBase64

object Base64EncodeDecode {
    fun String.encodeToBase64(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            JavaBase64.getUrlEncoder().encodeToString(this.toByteArray())
        } else {
            AndroidBase64.encodeToString(this.toByteArray(), 0)
        }
    }

    fun String.decodeFromBase64(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String(JavaBase64.getUrlDecoder().decode(this))
        } else {
            String(AndroidBase64.decode(this, 0))
        }
    }
}