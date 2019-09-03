package com.doug.simulation.simulate.data

import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal

interface SimulateApi {

    @GET("/calculator/simulate/")
    suspend fun simulate(
        @Query("investedAmount") investedAmount: BigDecimal,
        @Query("index") index: String,
        @Query("rate") rate: String,
        @Query("isTaxFree") taxFree: Boolean,
        @Query("maturityDate") maturityDate: String
    ): SimulationResponse

}