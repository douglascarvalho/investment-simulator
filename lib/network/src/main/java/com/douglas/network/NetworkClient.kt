package com.douglas.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()

    fun retrofit(url: String): Retrofit =
            Retrofit.Builder()
                .baseUrl(url)
                .client(OkHttpProvider.instance)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

}