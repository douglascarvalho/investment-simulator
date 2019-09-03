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

    fun simulate(
        amount: Double,
        maturityDate: String,
        rate: String
    ) {
        launch {
            val request = SimulationRequest(
                investedAmount = BigDecimal(amount), rate = rate, maturityDate = maturityDate
            )

            val simulationResponse = simulateRepository.simulate(request)

            state.value = SimulateViewState.Success
        }
    }

}