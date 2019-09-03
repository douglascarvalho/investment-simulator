package com.douglas.core

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat
import java.lang.Exception
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val START_RANGE = 3
private const val END_RANGE = 2

private const val PERCENT_MAX_LENGTH = 4
private const val DATE_MAX_LENGTH = 10

private const val DATE_FIRST_SLASH_POSITION = 2
private const val DATE_LAST_SLASH_POSITION = 5

const val HUNDRED = 100

fun EditText.setupBrazilianCurrencyFormat(formValidation: FormValidation) {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@setupBrazilianCurrencyFormat.removeTextChangedListener(this)

            var formatted = ""
            val cleanString = s.toString()
                .replace("R$", "")
                .replace(",", "")
                .replace(".", "")

            if (!cleanString.isEmpty()) {
                val parsed = cleanString.toDouble()
                formatted = NumberFormat
                    .getCurrencyInstance(Locale("pt_Br", "Br"))
                    .format(parsed / HUNDRED)

                formatted = formatted.run {
                    replaceRange(length - START_RANGE, length - END_RANGE, ",")
                }
            }

            this@setupBrazilianCurrencyFormat.setText(formatted)
            this@setupBrazilianCurrencyFormat.setSelection(formatted.length)
            this@setupBrazilianCurrencyFormat.addTextChangedListener(this)
            formValidation.validate()
        }
    })
}

fun EditText.setupDateFormat(formValidation: FormValidation) {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@setupDateFormat.removeTextChangedListener(this)
            var date = s.toString()
            if ((before == 0) &&
                (date.length == DATE_FIRST_SLASH_POSITION ||
                        date.length == DATE_LAST_SLASH_POSITION)) {
                date += "/"
            }

            if (date.length > DATE_MAX_LENGTH) {
                date = date.removeRange(DATE_MAX_LENGTH - 1, DATE_MAX_LENGTH)
            }
            this@setupDateFormat.setText(date)
            this@setupDateFormat.setSelection(date.length)
            this@setupDateFormat.addTextChangedListener(this)

            if (date.length == DATE_MAX_LENGTH) {
                if (date.isValidDate()) {
                    this@setupDateFormat.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                } else {
                    this@setupDateFormat.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
                }
            }
            formValidation.validate()
        }
    })
}

fun EditText.setupPercentFormat(formValidation: FormValidation) {

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            this@setupPercentFormat.removeTextChangedListener(this)
            var percent = s.toString().replace("%", "")
            if (percent.length == PERCENT_MAX_LENGTH) {
                percent = percent.removeRange(PERCENT_MAX_LENGTH - 1, PERCENT_MAX_LENGTH)
            }

            this@setupPercentFormat.setText(percent)
            this@setupPercentFormat.setSelection(percent.length)
            this@setupPercentFormat.addTextChangedListener(this)
            formValidation.validate()
        }
    })
}

fun String.isValidDate() = try {
    val typedDate = SimpleDateFormat("dd/MM/yyyy", Locale("pt-BR")).parse(this)
    typedDate > Date()
} catch (ex: Exception) {
    false
}

fun String.toServerCurrency() =
    this.replace("R$", "")
        .replace(".", "")
        .replace(",", "")
        .toDouble() / HUNDRED

fun String.toDisplayDate() = this.toDate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy")

fun String.toServerDate() = this.toDate("dd/MM/yyyy", "yyyy-MM-dd")

fun String.toDate(input: String, output: String): String {
    val locale = Locale("pt-BR")
    val inputFormat = SimpleDateFormat(input, locale)
    val outputFormat = SimpleDateFormat(output, locale)
    val d = inputFormat.parse(this)
    return outputFormat.format(d)
}

fun Double.toBrazilianCurrency() = NumberFormat
    .getCurrencyInstance(Locale("pt_BR", "BR"))
    .format(this).run {
        replaceRange(
            length - START_RANGE,
            length - END_RANGE,
            ",")
    }

fun Double.toPercent() = String.format("%.2f", this).plus("%")