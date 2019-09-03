package com.doug.simulation.simulate

import androidx.lifecycle.Observer
import com.doug.simulation.data.SimulationResponse
import com.doug.simulation.data.source.SimulateRepository
import com.doug.simulation.instantLiveDataAndCoroutineRule
import com.doug.simulation.result.SimulationResult
import com.doug.simulation.result.SimulationResultMapper
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SimulateViewModelTest {

    @get:Rule
    val rule = instantLiveDataAndCoroutineRule

    private val observer: Observer<SimulateViewState> = mock()

    private val repository: SimulateRepository = mock()
    private val mapper: SimulationResultMapper = mock()

    private val viewModel = SimulateViewModel(repository, mapper)

    private val simulationResponse: SimulationResponse = mock()
    private val simulationResult: SimulationResult = mock()

    private val investedAmount = 10.toDouble()
    private val rate = "100"
    private val maturityDate = "05/05/2020"

    @Before
    fun setup() {
        viewModel.viewState.observeForever(observer)
    }

    @Test
    fun givenNullSimualtionResponse_WhenRequest_ShouldOnlyEmitNetworkError() {
        whenever(runBlocking {
            repository.simulate(any())
        }) doReturn null

        viewModel.simulate(investedAmount, rate, maturityDate)

        verify(mapper, never()).mapToSimulationResult(any())
        verify(observer, never()).onChanged(SimulateViewState.Success(simulationResult))
        verify(observer, never()).onChanged(SimulateViewState.Error)
        verify(observer, times(1)).onChanged(SimulateViewState.NetworkError)

        verifyNoMoreInteractions(observer)
        verifyNoMoreInteractions(mapper)
    }

    @Test
    fun givenNullSimualtionResponse_WhenMappingError_ShouldOnlyEmitError() {
        whenever(runBlocking {
            repository.simulate(any())
        }) doReturn simulationResponse
        whenever(runBlocking {
            mapper.mapToSimulationResult(simulationResponse)
        }) doThrow RuntimeException()

        viewModel.simulate(investedAmount, rate, maturityDate)

        verify(observer, never()).onChanged(SimulateViewState.Success(simulationResult))
        verify(observer, never()).onChanged(SimulateViewState.NetworkError)
        verify(observer, times(1)).onChanged(SimulateViewState.Error)
        verify(mapper, times(1)).mapToSimulationResult(any())

        verifyNoMoreInteractions(observer)
        verifyNoMoreInteractions(mapper)    }

    @Test
    fun givenValidSimulationResponse_WhenRequest_ShouldOnlyEmitSuccess() {
        whenever(runBlocking {
            repository.simulate(any())
        }) doReturn simulationResponse
        whenever(runBlocking {
            mapper.mapToSimulationResult(simulationResponse)
        }) doReturn simulationResult

        viewModel.simulate(investedAmount, rate, maturityDate)

        verify(mapper, times(1)).mapToSimulationResult(simulationResponse)
        verify(observer, times(1)).onChanged(SimulateViewState.Success(simulationResult))
        verify(observer, never()).onChanged(SimulateViewState.NetworkError)
        verify(observer, never()).onChanged(SimulateViewState.Error)

        verifyNoMoreInteractions(observer)
        verifyNoMoreInteractions(mapper)
    }

}