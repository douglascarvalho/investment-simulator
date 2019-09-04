package com.doug.ui.validators

import com.douglas.core.HUNDRED

object AmountValidator : UiValidator {

    override fun isValid(value: String): Boolean {
        val value = value.replace("R$", "")
            .replace(".", "")
            .replace(",", "")
            .trim()

        return when (value.toFloatOrNull()) {
            null -> false
            else -> value.toFloat() / HUNDRED > 0.00
        }
    }

}