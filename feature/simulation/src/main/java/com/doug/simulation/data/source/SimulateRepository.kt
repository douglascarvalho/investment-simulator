package com.doug.simulation.data.source

import com.doug.simulation.data.SimulateApi
import com.doug.simulation.data.SimulationRequest
import com.doug.simulation.data.SimulationResponse
import java.io.IOException

class SimulateRepository(
    private val simulateApi: SimulateApi
) {

   suspend fun simulate(simulationRequest: SimulationRequest): SimulationResponse? =
       try {
           simulateApi.simulate(
               simulationRequest.investedAmount,
               simulationRequest.index,
               simulationRequest.rate,
               simulationRequest.isTaxFree,
               simulationRequest.maturityDate
           )
       } catch (ioException: IOException) {
           null
       }
}