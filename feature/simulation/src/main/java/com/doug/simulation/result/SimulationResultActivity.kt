package com.doug.simulation.result

import android.os.Bundle
import android.widget.TextView
import com.doug.simulation.R
import com.doug.ui.InvestmentTextView
import com.douglas.core.*

class SimulationResultActivity : BaseActivity(){

    private val totalValue by bindView<TextView>(R.id.txt_result_total_value)
    private val totalRevenue by bindView<TextView>(R.id.txt_result_total_revenue)
    private val lblInitialValue by bindView<InvestmentTextView>(R.id.lbl_initial_value)
    private val lblTotalValue by bindView<InvestmentTextView>(R.id.lbl_total_value)
    private val lblRevenueValue by bindView<InvestmentTextView>(R.id.lbl_revenue_value)
    private val lblTaxValue by bindView<InvestmentTextView>(R.id.lbl_tax_value)
    private val lblNetValue by bindView<InvestmentTextView>(R.id.lbl_net_value)
    private val lblDate by bindView<InvestmentTextView>(R.id.lbl_date)
    private val lblCountedDaye by bindView<InvestmentTextView>(R.id.lbl_counted_days)
    private val lblMonthlyRevenue by bindView<InvestmentTextView>(R.id.lbl_monthly_revenue)
    private val lblCDIPercent by bindView<InvestmentTextView>(R.id.lbl_cdi_percent)
    private val lblAnnualRevenue by bindView<InvestmentTextView>(R.id.lbl_annual_revenue)
    private val lblPeriodProfitability by bindView<InvestmentTextView>(R.id.lbl_period_profitability)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val simulationResult = getSimulationResult()

        totalValue.text = simulationResult.grossAmount.toBrazilianCurrency()
        totalRevenue.text = getString(R.string.result_description, simulationResult.grossAmountProfit.toBrazilianCurrency())
        lblInitialValue.setText(simulationResult.investedAmount.toBrazilianCurrency())
        lblTotalValue.setText(simulationResult.grossAmount.toBrazilianCurrency())
        lblRevenueValue.setText(simulationResult.grossAmountProfit.toBrazilianCurrency())
        lblTaxValue.setText("${simulationResult.taxesAmount.toBrazilianCurrency()}(${simulationResult.taxesRate.toPercent()})")
        lblNetValue.setText(simulationResult.netAmount.toBrazilianCurrency())
        lblDate.setText(simulationResult.maturityDate.toDisplayDate())
        lblCountedDaye.setText(simulationResult.maturityTotalDays.toString())
        lblMonthlyRevenue.setText(simulationResult.monthlyGrossRateProfit.toPercent())
        lblCDIPercent.setText(simulationResult.investmentRate.toPercent())
        lblAnnualRevenue.setText(simulationResult.annualGrossRateProfit.toPercent())
        lblPeriodProfitability.setText(simulationResult.annualNetRateProfit.toPercent())
    }

    private fun getSimulationResult() = intent?.extras?.get(SIMULATION_RESULT_KEY) as SimulationResult

    override fun initializeInjection() {}

}