package com.doug.simulation.result

import com.doug.simulation.data.SimulationResponse
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.io.FileReader

const val DOUBLE_DELTA = 000.1

class SimulationResultMapperTest {

    private val simulationResultMapper = SimulationResultMapper()
    lateinit var simulationResponse: SimulationResponse

    @Test
    fun givenValidResponseWhenMappedThenReturnMappedObject() {
        simulationResponse = getResponseFromJson("simulateResponse.json")

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
        simulationResponse = getResponseFromJson("invalidSimulateResponse.json")
        simulationResultMapper.mapToSimulationResult(simulationResponse)
    }

    private fun getResponseFromJson(fileName: String) : SimulationResponse {
        val reader = JsonReader(FileReader("src/test/resources/json/${fileName}"))
        return Gson().fromJson(reader, SimulationResponse::class.java)
    }

}