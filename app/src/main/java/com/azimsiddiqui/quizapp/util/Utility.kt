package com.azimsiddiqui.quizapp.util

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.formattedDate(pattern: String, date: Long): String {
    return SimpleDateFormat(pattern).format(Date(date))
}