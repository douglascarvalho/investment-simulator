package com.doug.ui.validators.ui

import com.douglas.core.HUNDRED

object AmountValidator : UiValidator {

    override fun isValid(value: String): Boolean {
        val validationValue = value.replace("R$", "")
            .replace(".", "")
            .replace(",", "")
            .trim()

        return when (validationValue.toFloatOrNull()) {
            null -> false
            else -> validationValue.toFloat() / HUNDRED > 0.00
        }
    }

}