package com.doug.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import com.doug.ui.mask.Mask
import com.doug.ui.validators.AmountValidator
import com.doug.ui.validators.UiValidator

class InvestmentEditText(context: Context?, attrs: AttributeSet?) : EditText(context, attrs) {

    private lateinit var uiValidator: UiValidator

    private val stateError = intArrayOf(R.attr.state_error)
    private var hasBeenEdited = false
    private var shouldShowError = false
    private var hasFocus = false

    init {
        val attributes = context?.obtainStyledAttributes(attrs, R.styleable.InvestmentEditText)
        val type = attributes?.getString(0)

        if (type == "monetary") {
            uiValidator = AmountValidator
            addTextChangedListener(Mask.brazilianMonetaryFormat( this))
        }
        init()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 2)
        if (shouldShowError) {
            View.mergeDrawableStates(drawableState, stateError)
        }
        return drawableState
    }

    fun forceUpdateState() {
        shouldShowError = !uiValidator.isValid(text.toString())
        refreshDrawableState()
    }

    fun isValid(): Boolean {
        return uiValidator.isValid(text.toString())
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                hasBeenEdited = true
                forceUpdateState()
            }
        })

        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            this.hasFocus = hasFocus
            updateState()
        }
    }

    private fun updateState() {
        shouldShowError = !hasFocus && !uiValidator.isValid(text.toString()) && hasBeenEdited
        refreshDrawableState()
    }
}