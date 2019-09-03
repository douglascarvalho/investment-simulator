package com.doug.simulation.simulate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doug.simulation.result.SimulationResultMapper
import com.doug.simulation.data.model.SimulationRequest
import com.doug.simulation.data.source.SimulateRepository
import com.douglas.core.BaseViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.math.BigDecimal

class SimulateViewModel(
    private val simulateRepository: SimulateRepository,
    private val resultMapper: SimulationResultMapper
) : BaseViewModel() {

    private val state = MutableLiveData<SimulateViewState>()
    val viewState: LiveData<SimulateViewState> = state

    fun simulate(
        amount: Double,
        maturityDate: String,
        rate: String
    ) {
        launch {
            try {
                val request = SimulationRequest(
                    investedAmount = BigDecimal(amount), rate = rate, maturityDate = maturityDate
                )
                val simulationResponse = simulateRepository.simulate(request)

                if (simulationResponse == null) {
                    state.value = SimulateViewState.NetworkError
                    return@launch
                }

                val result = resultMapper.mapToSimulationResult(simulationResponse)
                state.value = SimulateViewState.Success(result)
            } catch (e: Exception) {
                state.value = SimulateViewState.Error
            }
        }
    }

}