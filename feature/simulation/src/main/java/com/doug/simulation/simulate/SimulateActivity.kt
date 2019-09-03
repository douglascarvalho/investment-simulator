package com.doug.simulation.simulate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.doug.simulation.R
import com.doug.simulation.result.SIMULATION_RESULT_KEY
import com.doug.simulation.result.SimulationResult
import com.doug.simulation.result.SimulationResultActivity
import com.doug.simulation.simulate.injection.initializeSimulateModule
import com.douglas.core.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimulateActivity : BaseActivity(), FormValidation{

    private val amount: EditText by bindView(R.id.amount)
    private val maturityDate: EditText by bindView(R.id.maturity_date)
    private val rate: EditText by bindView(R.id.rate)

    private val simulateButton: Button by bindView(R.id.simulate)

    private val simulateViewModel: SimulateViewModel by viewModel()

    override fun initializeInjection() {
        initializeSimulateModule()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulate)

        observeViewModel()

        setupSimulateForm()
        setupSimulateButton()
    }

    private fun setupSimulateForm() {
        amount.setupBrazilianCurrencyFormat(this)
        maturityDate.setupDateFormat(this)
        rate.setupPercentFormat(this)
    }

    override fun validate() {
        simulateButton.isEnabled =
            isValidAmount(amount.text.toString())
                    && isValidRate(rate.text.toString())
                    && isValidDate(maturityDate.text.toString())
    }

    private fun setupSimulateButton() {
        simulateButton.onClick {
            simulateViewModel.simulate(
                amount.text.toString().toServerCurrency(),
                maturityDate.text.toString().toServerDate(),
                rate.text.toString()
            )
        }
    }

    private fun observeViewModel() {
        simulateViewModel.viewState.observe(this, Observer {
            when (it) {
                is SimulateViewState.Success -> initResultActivity(it.simulationResult)
                is SimulateViewState.Error -> initErrorActivity()
            }
        })
    }

    private fun initResultActivity(simulationResult: SimulationResult) {
        val intent = Intent(this, SimulationResultActivity::class.java)
        intent.putExtra(SIMULATION_RESULT_KEY, simulationResult)
        startActivity(intent)
    }

    private fun initErrorActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}