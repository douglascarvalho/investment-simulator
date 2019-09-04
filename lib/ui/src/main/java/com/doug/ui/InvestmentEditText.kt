package com.doug.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.doug.ui.mask.Mask
import com.doug.ui.validators.listener.ValidationListener
import com.doug.ui.validators.ui.AmountValidator
import com.doug.ui.validators.ui.DateValidator
import com.doug.ui.validators.ui.NoValidator
import com.doug.ui.validators.ui.UiValidator

class InvestmentEditText(context: Context?, attrs: AttributeSet?) : EditText(context, attrs) {

    private var uiValidator: UiValidator? = null
    private var validationListener: ValidationListener? = null

    private val stateError = intArrayOf(R.attr.state_error)
    private var hasBeenEdited = false
    private var shouldShowError = false
    private var hasFocus = false

    init {
        val attributes = context?.obtainStyledAttributes(attrs, R.styleable.InvestmentEditText)

        when (attributes?.getString(0)) {
            "monetary" -> setupMonetaryType()
            "date" -> setupDateType()
            "percent" -> setupPercentType()
            else -> Unit
        }
    }

    private fun setupMonetaryType() {
        addTextChangedListener(Mask.brazilianMonetaryFormat( this))
        initValidator(AmountValidator)
    }

    private fun setupPercentType() {
        addTextChangedListener(Mask.percentageFormat(this))
        initValidator(NoValidator)
    }

    private fun setupDateType() {
        addTextChangedListener(Mask.mask( "##/##/####",this))
        initValidator(DateValidator)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 2)
        if (shouldShowError) {
            View.mergeDrawableStates(drawableState, stateError)
        }
        return drawableState
    }

    fun forceUpdateState() {
        shouldShowError = !uiValidator!!.isValid(text.toString())
        refreshDrawableState()
    }

    fun isValid(): Boolean {
        return uiValidator?.isValid(text.toString()) ?: false
    }

    private fun initValidator(validator: UiValidator) {
        uiValidator = validator

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                hasBeenEdited = true
                forceUpdateState()
                validationListener?.validate()
            }
        })

        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            this.hasFocus = hasFocus
            updateState()
        }
    }

    private fun updateState() {
        shouldShowError = !hasFocus && !uiValidator!!.isValid(text.toString()) && hasBeenEdited
        refreshDrawableState()
    }

    fun setValidationListener(validationListener: ValidationListener) {
        this.validationListener = validationListener
    }
}