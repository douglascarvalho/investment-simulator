package com.doug.simulation.simulate

import android.os.Bundle
import androidx.lifecycle.Observer
import com.doug.simulation.R
import com.doug.simulation.simulate.injection.initializeSimulateModule
import com.douglas.core.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimulateActivity : BaseActivity() {

    private val simulateViewModel: SimulateViewModel by viewModel()

    override fun initializeInjection() {
        initializeSimulateModule()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulate)

        observeViewModel()

        setupSimulateButton()
    }

    private fun observeViewModel() {
        simulateViewModel.viewState.observe(this, Observer {

        })
    }

    private fun setupSimulateButton() {
        simulateViewModel.simulate()
    }
}