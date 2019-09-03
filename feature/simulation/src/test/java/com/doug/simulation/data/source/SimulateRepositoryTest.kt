package com.doug.simulation.data.source

import com.doug.simulation.data.SimulateApi
import com.doug.simulation.data.SimulationRequest
import com.doug.simulation.data.SimulationResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.`when`
import java.io.IOException
import java.math.BigDecimal

class SimulateRepositoryTest {

    private val simulateResponse: SimulationResponse = mock()
    private val simulateApi: SimulateApi = mock()
    private val repository = SimulateRepository(simulateApi)

    @Test
    fun givenSimulationRequest_whenServerReturnNull_thenShouldReturnNullToViewModel() {
        val simulationRequest = createSimulationRequest()

        whenever(runBlocking {
            simulateApi.simulate(
                simulationRequest.investedAmount,
                simulationRequest.index,
                simulationRequest.rate,
                simulationRequest.isTaxFree,
                simulationRequest.maturityDate
            )
        }) doReturn null

        val result = runBlocking { repository.simulate(simulationRequest) }

        assertNull(result)
    }

    @Test
    fun givenSimulationRequest_whenClientThrowsException_thenShouldReturnNullToViewModel() {
        val simulationRequest = createSimulationRequest()

        `when`(runBlocking {
            simulateApi.simulate(
                simulationRequest.investedAmount,
                simulationRequest.index,
                simulationRequest.rate,
                simulationRequest.isTaxFree,
                simulationRequest.maturityDate
            )
        }).thenAnswer { throw IOException() }

        val result = runBlocking { repository.simulate(simulationRequest) }

        assertNull(result)
    }

    @Test
    fun givenSimulationRequest_whenServerReturnValidResponse_thenShouldReturnValidResponseToViewModel() {
        val simulationRequest = createSimulationRequest()

        whenever(runBlocking {
            simulateApi.simulate(
                simulationRequest.investedAmount,
                simulationRequest.index,
                simulationRequest.rate,
                simulationRequest.isTaxFree,
                simulationRequest.maturityDate
            )
        }) doReturn simulateResponse

        val result = runBlocking { repository.simulate(simulationRequest) }

        assertNotNull(result)
        assertEquals(simulateResponse, result)
    }

    private fun createSimulationRequest() =
        SimulationRequest(
            investedAmount = BigDecimal(10),
            rate = "100",
            maturityDate = "05/05/2020"
        )
}