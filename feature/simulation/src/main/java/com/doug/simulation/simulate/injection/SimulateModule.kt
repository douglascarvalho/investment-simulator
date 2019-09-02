package com.doug.simulation.simulate.injection

import com.doug.simulation.simulate.SimulateViewModel
import com.doug.simulation.simulate.data.SimulateApi
import com.doug.simulation.simulate.data.source.SimulateRepository
import com.douglas.network.NetworkClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal fun initializeSimulateModule() = loadKoinModules(simulateModule)

private const val URL = "https://api-simulator-calc.easynvest.com.br"

val simulateModule = module {

    single { NetworkClient.retrofit(URL).create(SimulateApi::class.java) }

    single { SimulateRepository(get()) }

    viewModel { SimulateViewModel(get()) }

}

