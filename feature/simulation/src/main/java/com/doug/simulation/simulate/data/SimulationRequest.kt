package com.doug.simulation.simulate.data

import java.math.BigDecimal
import java.util.Date

data class SimulationRequest(
    val investedAmount: BigDecimal,
    val index: String,
    val rate: Int,
    val isTaxFree: Boolean,
    val maturityDate: String
)

/**
investedAmount = 32323.0                 // Valor a investir em reais
index = "CDI"                            // Índice, por enquanto só CDI disponível
rate = 123                               // Percentual do papel
isTaxFree = false                        // Isento de IR, por enquanto só falso
maturityDate = "2023-03-03"              // Data do vencimento, no formato ano-mes-dia
*/