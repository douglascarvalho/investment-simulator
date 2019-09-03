package com.doug.ui

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.douglas.core.bindView

class InvestmentTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val label by bindView<TextView>(R.id.txt_labeled_label)
    private val textValue by bindView<TextView>(R.id.txt_labeled_value)

    init {
        inflate(context, R.layout.investment_text_view, rootView as ViewGroup?)
        context.theme.obtainStyledAttributes(attrs, R.styleable.InvestmentTextView, 0, 0).apply {
            try {
                label.text = getText(R.styleable.InvestmentTextView_android_label)
            } finally {
                recycle()
            }
        }
    }

    fun setText(nText: String) {
        textValue.text = nText
    }
}