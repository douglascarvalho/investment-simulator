package com.douglas.core

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val START_RANGE = 3
private const val END_RANGE = 2

const val HUNDRED = 100

fun String.toServerCurrency() =
    this.replace("R$", "")
        .replace(".", "")
        .replace(",", "")
        .trim()
        .toDouble() / HUNDRED

fun String.toDisplayDate() = this.toDate("yyyy-MM-dd'T'HH:mm:ss", "dd/MM/yyyy")

fun String.toServerDate() = this.toDate("dd/MM/yyyy", "yyyy-MM-dd")

fun String.toServerPercentage() = this.replace("%", "")

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