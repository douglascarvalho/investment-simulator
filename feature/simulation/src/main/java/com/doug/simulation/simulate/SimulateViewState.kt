package com.doug.simulation.simulate

sealed class SimulateViewState {
    object Success : SimulateViewState()
    object Error : SimulateViewState()
    object NetworkError : SimulateViewState()
}