package com.doug.simulation.simulate.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigDecimal
import java.util.*

private const val INVESTED_AMOUNT = "investedAmount"
private const val INDEX = "index"
private const val RATE = "rate"
private const val TAX_FREE = "isTaxFree"
private const val MATURITY_DATE = "maturityDate"

interface SimulateApi {

    @GET("/calculator/simulate/")
    suspend fun simulate(
        @Query(INVESTED_AMOUNT) investedAmount: BigDecimal,
        @Query(INDEX) index: String,
        @Query(RATE) rate: Int,
        @Query(TAX_FREE) taxFree: Boolean,
        @Query(MATURITY_DATE) maturityDate: String
    ): SimulationResponse

}