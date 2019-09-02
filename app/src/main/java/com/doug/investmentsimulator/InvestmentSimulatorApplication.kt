package com.doug.investmentsimulator

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InvestmentSimulatorApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@InvestmentSimulatorApplication)
        }
    }
}