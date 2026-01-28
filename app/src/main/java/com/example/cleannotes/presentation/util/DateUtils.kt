package com.example.cleannotes.presentation.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    // Para obtener el día: "18"
    fun getDayNumber(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    // Para obtener el mes: "DEC"
    fun getMonthName(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM", Locale.getDefault())
        return sdf.format(Date(timestamp)).uppercase()
    }

    // Para la hora: "1:00 PM"
    fun getTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}