package com.doug.simulation.simulate.data

import java.math.BigDecimal
import java.util.*

data class SimulationResponse(
    val investmentParameter: InvestmentParameter
)

data class InvestmentParameter(
    val investedAmount: BigDecimal?,
    val maturityDate: Date?,
    val maturityTotalDays: Int?,
    val rate: String?
)