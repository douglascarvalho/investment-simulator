package com.doug.simulation.simulate.data.source

import com.doug.simulation.simulate.data.SimulateApi
import com.doug.simulation.simulate.data.SimulationRequest

class SimulateRepository(
    private val simulateApi: SimulateApi
) {

   suspend fun simulate(simulationRequest: SimulationRequest) =
       simulateApi.simulate(
           simulationRequest.investedAmount,
           simulationRequest.index,
           simulationRequest.rate,
           simulationRequest.isTaxFree,
           simulationRequest.maturityDate
       )
}