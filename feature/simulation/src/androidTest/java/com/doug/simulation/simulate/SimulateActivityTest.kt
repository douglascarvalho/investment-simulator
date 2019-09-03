package com.doug.simulation.simulate

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.doug.simulation.EMPTY_SIMULATION_RESPONSE_FILE
import com.doug.simulation.INVALID_SIMULATION_RESPONSE_FILE
import com.doug.simulation.VALID_SIMULATION_RESPONSE_FILE
import com.doug.simulation.data.SimulateApi
import com.doug.simulation.data.source.SimulateRepository
import com.doug.simulation.injection.initializeSimulateModule
import com.doug.simulation.result.SimulationResultMapper
import com.douglas.network.NetworkClient
import io.mockk.every
import io.mockk.mockkStatic
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
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
        stopKoin()
    }

    @Test
    fun givenValidServerResponse_whenSimulate_shouldDisplaySimulationResultScreen() {
        simulate {
            setupServer(server, VALID_SIMULATION_RESPONSE_FILE)
            startActvity(activityRule)

            withAmount("500")
            withMaturityDate("05052020")
            withRate("50")

            clickSimulateButton()

            checkResultIsDisplayed()
            checkSimulateAgainButtonIsDisplayed()
        }
    }

    @Test
    fun givenNullServerResponse_whenSimulate_shouldDisplayNetworkErrorMessage() {
        simulate {
            setupServer(server, EMPTY_SIMULATION_RESPONSE_FILE)
            startActvity(activityRule)

            withAmount("500")
            withMaturityDate("05052020")
            withRate("50")

            clickSimulateButton()

            checkErrorIsDisplayed("Ops, estamos em manutenção, voltamos já!")
        }
    }

    @Test
    fun givenInvalidServerResponse_whenSimulate_shouldDisplayErrorMessage() {
        simulate {
            setupServer(server, INVALID_SIMULATION_RESPONSE_FILE)
            startActvity(activityRule)

            withAmount("500")
            withMaturityDate("05052020")
            withRate("50")

            clickSimulateButton()

            checkErrorIsDisplayed("Ops, tivemos um problema com a sua requisição!")
        }
    }

    @Test
    fun givenInvalidMaturityDate_whenTrySimulate_shouldNotEnableButton() {
        simulate {
            startActvity(activityRule)

            withAmount("500")
            withMaturityDate("0505202")
            withRate("50")

            checkSimulateButtonIsDisabled()
        }
    }

    @Test
    fun givenInvalidAmount_whenTrySimulate_shouldNotEnableButton() {
        simulate {
            startActvity(activityRule)

            withAmount("0")
            withMaturityDate("05052020")
            withRate("50")

            checkSimulateButtonIsDisabled()
        }
    }

    @Test
    fun givenInvalidRate_whenTrySimulate_shouldNotEnableButton() {
        simulate {
            startActvity(activityRule)

            withAmount("100")
            withMaturityDate("05052020")

            checkSimulateButtonIsDisabled()
        }
    }

}