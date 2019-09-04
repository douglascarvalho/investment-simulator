package com.doug.ui.validators.ui

object NoValidator : UiValidator {
    override fun isValid(value: String) = true
}