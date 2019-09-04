package com.doug.ui.validators.ui

import java.util.*

private const val A_YEAR_FAR_FAR_AWAY = 3000

object DateValidator : UiValidator {

    private val currentDate = Calendar.getInstance()

    override fun isValid(value: String): Boolean {
        val date = value.split("/").dropLastWhile { it == "" }

        if (date.isEmpty()) return false

        val day = date[0].toInt()
        val month = date.getOrNull(1)?.toInt()
        val year = date.getOrNull(2)?.toInt()

        if (day in 1..30) {
            if (month != null && month in 1..12) {
                if (year != null && year in currentDate.get(Calendar.YEAR)..A_YEAR_FAR_FAR_AWAY) {
                    return true
                }
            }
        }

        return false
    }
}