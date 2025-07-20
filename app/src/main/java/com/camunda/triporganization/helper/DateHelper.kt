package com.camunda.triporganization.helper

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateHelper {
    fun convertMillisToDate(millis: Long?): String {
        if (millis == null) return ""
        val formatter = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}
