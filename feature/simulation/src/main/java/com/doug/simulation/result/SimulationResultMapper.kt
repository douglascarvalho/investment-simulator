package com.doug.simulation.result

import com.doug.simulation.simulate.data.SimulationResponse

class SimulationResultMapper {

    fun mapToSimulationResult(simulationResponse: SimulationResponse) =
        SimulationResult(
            grossAmount = simulationResponse.grossAmount,
            grossAmountProfit = simulationResponse.grossAmountProfit,
            investedAmount = simulationResponse.investmentResponse.investedAmount,
            taxesAmount = simulationResponse.taxesAmount,
            taxesRate = simulationResponse.taxesRate,
            netAmount = simulationResponse.netAmount,
            maturityDate = simulationResponse.investmentResponse.maturityDate,
            maturityTotalDays = simulationResponse.investmentResponse.maturityTotalDays,
            monthlyGrossRateProfit =  simulationResponse.monthlyGrossRateProfit,
            investmentRate = simulationResponse.investmentResponse.rate,
            annualGrossRateProfit = simulationResponse.annualGrossRateProfit,
            annualNetRateProfit = simulationResponse.annualNetRateProfit
        )
}