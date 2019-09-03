package com.doug.simulation.simulate

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.doug.simulation.JsonReader
import com.doug.simulation.R
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not

fun simulate(func: SimulateActivityRobot.() -> Unit) = SimulateActivityRobot().apply { func() }

class SimulateActivityRobot {

    fun startActvity(activityRule: ActivityTestRule<SimulateActivity>) = activityRule.launchActivity(null)

    fun setupServer(server: MockWebServer, jsonFile: String) {
        server.enqueue(
            MockResponse().setResponseCode(200).setBody(
                JsonReader.getStringFromJsonFile(jsonFile)
            ))
    }

    fun withAmount(amount: String) = onView(withId(R.id.amount))
        .perform(click())
        .perform(typeText(amount)).also {
            Espresso.pressBack()
        }

    fun withMaturityDate(maturityDate: String) = onView(withId(R.id.maturity_date))
        .perform(click())
        .perform(typeText(maturityDate)).also {
            Espresso.pressBack()
        }

    fun withRate(rate: String) = onView(withId(R.id.rate))
        .perform(click())
        .perform(typeText(rate)).also {
            Espresso.pressBack()
        }

    fun clickSimulateButton() = onView(withId(R.id.simulate)).perform(click())

    fun checkResultIsDisplayed() =
        onView(withText("Resultado da simulação")).check(matches(isDisplayed()))

    fun checkSimulateAgainButtonIsDisplayed() =
        onView(withText("Simular novamente")).check(matches(isDisplayed()))

    fun checkErrorIsDisplayed(errorMessage: String) =
        onView(withId(R.id.errorMessage))
            .check(matches(isDisplayed()))
            .check(matches(withText(errorMessage)))

    fun checkSimulateButtonIsDisabled() =
        onView(withId(R.id.simulate))
            .check(matches(isDisplayed()))
            .check(matches(not(isEnabled())))

}