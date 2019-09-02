package com.doug.investmentsimulator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doug.simulation.simulate.SimulateActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialIntent = Intent(this, SimulateActivity::class.java)
        startActivity(initialIntent)
    }
}
