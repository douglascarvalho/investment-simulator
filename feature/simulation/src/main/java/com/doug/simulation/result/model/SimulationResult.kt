package com.doug.simulation.result.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val SIMULATION_RESULT_KEY = "SimulationResult"

@Parcelize
data class SimulationResult (

    val grossAmount: Double,
    val grossAmountProfit: Double,
    val investedAmount: Double,
    val taxesAmount: Double,
    val netAmount: Double,
    val maturityDate: String,
    val maturityTotalDays: Int,
    val monthlyGrossRateProfit: Double,
    val taxesRate: Double,
    val investmentRate: Double,
    val annualGrossRateProfit: Double,
    val annualNetRateProfit: Double

) : Parcelable