package com.doug.simulation.result

import android.os.Parcel
import android.os.Parcelable

const val SIMULATION_RESULT_KEY = "SimulationResult"

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

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(grossAmount)
        parcel.writeDouble(grossAmountProfit)
        parcel.writeDouble(investedAmount)
        parcel.writeDouble(taxesAmount)
        parcel.writeDouble(netAmount)
        parcel.writeString(maturityDate)
        parcel.writeInt(maturityTotalDays)
        parcel.writeDouble(monthlyGrossRateProfit)
        parcel.writeDouble(taxesRate)
        parcel.writeDouble(investmentRate)
        parcel.writeDouble(annualGrossRateProfit)
        parcel.writeDouble(annualNetRateProfit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SimulationResult> {
        override fun createFromParcel(parcel: Parcel): SimulationResult {
            return SimulationResult(parcel)
        }

        override fun newArray(size: Int): Array<SimulationResult?> {
            return arrayOfNulls(size)
        }
    }
}