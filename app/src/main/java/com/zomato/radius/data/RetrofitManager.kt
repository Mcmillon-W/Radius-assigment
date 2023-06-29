package com.application.services.partner.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private val API_BASE_URL = "https://my-json-server.typicode.com"

    private var retrofit: Retrofit? = null
    private val gson =
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().setLenient().create()

    fun <S> createRetrofitService(serviceClass: Class<S>): S {
        synchronized(RetrofitManager::class.java) {
            if (retrofit == null) {
                resetRetrofitClient()
            }
            return retrofit!!.create(serviceClass)
        }
    }

    fun resetRetrofitClient() {
        retrofit = Retrofit.Builder().apply {
            baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

}