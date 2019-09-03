package com.doug.simulation.simulate

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.doug.simulation.R
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

    private fun observeViewModel() {
        simulateViewModel.viewState.observe(this, Observer {

        })
    }

    override fun validate() {
        simulateButton.isEnabled = isValidAmount() && isValidRate() && isValidDate()
    }

    private fun isValidAmount() : Boolean {
        val value = amount.text.toString().replace("R$", "")
            .replace(".", "")
            .replace(",", "")

        return when (value.toFloatOrNull()) {
            null -> false
            else -> value.toFloat() / HUNDRED > 0.00
        }
    }

    private fun isValidRate() = when (rate.text.toString().toIntOrNull()) {
        null -> false
        else -> true
    }

    private fun isValidDate() = maturityDate.text.toString().isValidDate()

    private fun setupSimulateButton() {
        simulateButton.onClick {
            simulateViewModel.simulate(
                amount.text.toString().toServerCurrency(),
                maturityDate.text.toString().toServerDate(),
                rate.text.toString()
            )
        }
    }
}