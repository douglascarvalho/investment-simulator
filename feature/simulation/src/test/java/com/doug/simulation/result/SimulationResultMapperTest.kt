package com.doug.simulation.result

import com.doug.simulation.INVALID_SIMULATION_RESPONSE_FILE
import com.doug.simulation.JsonReader.getObjectFromJsonFile
import com.doug.simulation.VALID_SIMULATION_RESPONSE_FILE
import com.doug.simulation.data.model.SimulationResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

const val DOUBLE_DELTA = 000.1

class SimulationResultMapperTest {

    private val simulationResultMapper = SimulationResultMapper()
    lateinit var simulationResponse: SimulationResponse

    @Test
    fun givenValidResponseWhenMappedThenReturnMappedObject() {
        simulationResponse = getObjectFromJsonFile(VALID_SIMULATION_RESPONSE_FILE)

        val mapped = simulationResultMapper.mapToSimulationResult(simulationResponse)

        assertNotNull(mapped)
        assertEquals(simulationResponse.grossAmount, mapped.grossAmount, DOUBLE_DELTA)
        assertEquals(simulationResponse.grossAmountProfit, mapped.grossAmountProfit, DOUBLE_DELTA)
        assertEquals(simulationResponse.annualGrossRateProfit, mapped.annualGrossRateProfit, DOUBLE_DELTA)
        assertEquals(simulationResponse.annualNetRateProfit, mapped.annualNetRateProfit, DOUBLE_DELTA)
        assertEquals(simulationResponse.monthlyGrossRateProfit, mapped.monthlyGrossRateProfit, DOUBLE_DELTA)
        assertEquals(simulationResponse.investmentResponse.investedAmount, mapped.investedAmount, DOUBLE_DELTA)
        assertEquals(simulationResponse.investmentResponse.maturityDate, mapped.maturityDate)
        assertEquals(simulationResponse.investmentResponse.maturityTotalDays, mapped.maturityTotalDays)
        assertEquals(simulationResponse.netAmount, mapped.netAmount, DOUBLE_DELTA)
        assertEquals(simulationResponse.taxesAmount, mapped.taxesAmount, DOUBLE_DELTA)
        assertEquals(simulationResponse.taxesRate, mapped.taxesRate, DOUBLE_DELTA)
    }

    @Test(expected = IllegalArgumentException::class)
    fun givenInvalidResponse_whenMapping_shoudThrownIllegalArgumentException() {
        simulationResponse = getObjectFromJsonFile(INVALID_SIMULATION_RESPONSE_FILE)
        simulationResultMapper.mapToSimulationResult(simulationResponse)
    }

}