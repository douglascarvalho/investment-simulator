package com.douglas.core

interface FormValidation {
    fun validate()

    fun isValidAmount(amount: String) : Boolean {
        val value = amount.replace("R$", "")
            .replace(".", "")
            .replace(",", "")

        return when (value.toFloatOrNull()) {
            null -> false
            else -> value.toFloat() / HUNDRED > 0.00
        }
    }

    fun isValidRate(rate: String) = when (rate.toIntOrNull()) {
        null -> false
        else -> true
    }

    fun isValidDate(maturityDate: String) = maturityDate.isValidDate()

}