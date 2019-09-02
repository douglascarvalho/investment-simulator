package com.doug.simulation.simulate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doug.simulation.simulate.data.SimulationRequest
import com.doug.simulation.simulate.data.source.SimulateRepository
import com.douglas.core.BaseViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal


class SimulateViewModel(
    private val simulateRepository: SimulateRepository
) : BaseViewModel() {

    private val state = MutableLiveData<SimulateViewState>()
    val viewState: LiveData<SimulateViewState> = state

    fun simulate() {
        launch {
            val request = SimulationRequest(BigDecimal(3232), "CDI", 123, false, "2023-03-03")
            val simulationResponse = simulateRepository.simulate(request)

            state.value = SimulateViewState.Success
        }
    }

}