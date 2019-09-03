package com.doug.simulation.simulate.data

import java.math.BigDecimal

data class SimulationRequest(
    val investedAmount: BigDecimal,
    val index: String = "CDI",
    val rate: String,
    val isTaxFree: Boolean = false,
    val maturityDate: String
)