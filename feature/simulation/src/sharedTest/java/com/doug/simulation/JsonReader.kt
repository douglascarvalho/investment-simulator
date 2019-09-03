package com.doug.simulation

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.BufferedReader
import java.io.FileReader

const val VALID_SIMULATION_RESPONSE_FILE = "simulateResponse.json"
const val INVALID_SIMULATION_RESPONSE_FILE = "invalidSimulateResponse.json"

const val FULL_JSON_FILE_PATH = "src/sharedTest/resources/json/"
const val JSON_FILE_PATH = "json/"

object JsonReader {

    inline fun <reified T> getObjectFromJsonFile(fileName: String) : T {
        val reader = JsonReader(FileReader(FULL_JSON_FILE_PATH + fileName))
        return Gson().fromJson<T>(reader, T::class.java)
    }

    fun getStringFromJsonFile(fileName: String) : String {
        val reader = BufferedReader(javaClass.classLoader?.getResourceAsStream(JSON_FILE_PATH + fileName)?.reader())
        var content = ""
        reader.use {
            content = reader.readText()
        }
        return content
    }
}