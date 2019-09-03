package com.doug.simulation.injection

import com.doug.simulation.result.SimulationResultMapper
import com.doug.simulation.simulate.SimulateViewModel
import com.doug.simulation.data.SimulateApi
import com.doug.simulation.data.source.SimulateRepository
import com.douglas.network.NetworkClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal fun initializeSimulateModule() = loadKoinModules(simulateModule)

private const val URL = "https://api-simulator-calc.easynvest.com.br"

val simulateModule = module {

    single { NetworkClient.retrofit(URL).create(SimulateApi::class.java) }

    single { SimulateRepository(get()) }

    single { SimulationResultMapper()  }

    viewModel { SimulateViewModel(get(), get()) }

}

