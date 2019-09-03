package com.doug.simulation.simulate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.doug.simulation.R
import com.doug.simulation.result.SIMULATION_RESULT_KEY
import com.doug.simulation.result.SimulationResult
import com.doug.simulation.result.SimulationResultActivity
import com.doug.simulation.injection.initializeSimulateModule
import com.douglas.core.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimulateActivity : BaseActivity(), FormValidation{

    private val progressBar: ProgressBar by bindView(R.id.progressBar)
    private val errorMessage: TextView by bindView(R.id.errorMessage)

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
            showLoading()
            hideErrorMessage()
            simulateViewModel.simulate(
                amount.text.toString().toServerCurrency(),
                maturityDate.text.toString().toServerDate(),
                rate.text.toString()
            )
        }
    }

    private fun observeViewModel() {
        simulateViewModel.viewState.observe(this, Observer {
            hideLoading()
            when (it) {
                is SimulateViewState.Success -> initResultActivity(it.simulationResult)
                is SimulateViewState.Error -> showErrorMessage()
            }
        })
    }

    private fun initResultActivity(simulationResult: SimulationResult) {
        val intent = Intent(this, SimulationResultActivity::class.java)
        intent.putExtra(SIMULATION_RESULT_KEY, simulationResult)
        startActivity(intent)
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showErrorMessage() {
        errorMessage.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        errorMessage.visibility = View.GONE
    }

}