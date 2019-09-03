package com.doug.simulation.simulate

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.doug.simulation.FULL_JSON_FILE_PATH
import com.doug.simulation.JsonReader
import com.doug.simulation.VALID_SIMULATION_RESPONSE_FILE
import com.doug.simulation.data.SimulateApi
import com.doug.simulation.data.source.SimulateRepository
import com.doug.simulation.injection.initializeSimulateModule
import com.doug.simulation.result.SimulationResultMapper
import com.douglas.network.NetworkClient
import io.mockk.every
import io.mockk.mockkStatic
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class SimulateActivityTest  {

    @get:Rule
    var activityRule = ActivityTestRule(SimulateActivity::class.java, false, false)

    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        val retrofit = NetworkClient
            .retrofit(server.url("/").toString())
            .create(SimulateApi::class.java)

        val simulateModule = module {
            single { retrofit }
            single { SimulateRepository(retrofit) }
            single { SimulationResultMapper() }
            viewModel { SimulateViewModel(get(), get()) }
        }

        startKoin {}
        mockkStatic("com.doug.simulation.injection.SimulateModuleKt")
        every { initializeSimulateModule() } returns loadKoinModules(simulateModule)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun useAppContext() {
        server.enqueue(MockResponse().setResponseCode(200).setBody(
            JsonReader.getStringFromJsonFile(VALID_SIMULATION_RESPONSE_FILE)
        ))

        activityRule.launchActivity(null)

        onView(ViewMatchers.withId(com.doug.simulation.R.id.amount))
            .perform(click())
            .perform(typeText("500"))

        Espresso.pressBack()

        onView(ViewMatchers.withId(com.doug.simulation.R.id.maturity_date))
            .perform(click())
            .perform(typeText("05052020"))

        Espresso.pressBack()

        onView(ViewMatchers.withId(com.doug.simulation.R.id.rate))
            .perform(click())
            .perform(typeText("50"))

        Espresso.pressBack()

        onView(ViewMatchers.withId(com.doug.simulation.R.id.simulate)).perform(click())
    }

}