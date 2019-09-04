package com.doug.ui.validators

interface UiValidator {
    fun isValid(value: String): Boolean
}