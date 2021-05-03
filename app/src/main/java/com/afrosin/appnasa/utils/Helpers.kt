package com.afrosin.appnasa.utils

import java.text.SimpleDateFormat
import java.util.*

fun getDate(addDay: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, addDay)

    return calendar.time
}

fun dateToStr(date: Date, dateFormat: String = "yyyy-MM-dd"): String {
    val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
    return simpleDateFormat.format(date)
}