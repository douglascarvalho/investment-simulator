package com.doug.simulation.simulate

import com.doug.simulation.result.SimulationResult

sealed class SimulateViewState {
    data class Success(val simulationResult: SimulationResult) : SimulateViewState()
    object Error : SimulateViewState()
    object NetworkError : SimulateViewState()
}