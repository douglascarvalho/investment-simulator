package com.doug.simulation.result

import com.doug.simulation.data.SimulationResponse
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.FileReader

const val DOUBLE_DELTA = 000.1

class SimulationResultMapperTest {

    private val simulationResultMapper = SimulationResultMapper()

    lateinit var simulationResponse: SimulationResponse

    @Before
    fun setup() {
        val reader =
            com.google.gson.stream.JsonReader(FileReader("src/test/resources/json/simulateResponse.json"))
        simulationResponse = Gson().fromJson(reader, SimulationResponse::class.java)
    }

    @Test
    fun testNotNull() {
        val mapped = simulationResultMapper.mapToSimulationResult(simulationResponse)

        assertNotNull(mapped)
    }

    @Test
    fun givenValidResponseWhenMappedThenReturnMappedObject() {
        val mapped = simulationResultMapper.mapToSimulationResult(simulationResponse)

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

}