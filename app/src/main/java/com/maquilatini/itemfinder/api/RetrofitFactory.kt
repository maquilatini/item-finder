package com.maquilatini.itemfinder.api

import com.google.gson.GsonBuilder
import com.maquilatini.itemfinder.BuildConfig.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Interface to Retrofit.
 */
interface RetrofitFactory {

    companion object {
        private val instance: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }

        fun <T> getServiceRetrofit(service: Class<T>): T = instance.create(service)
    }
}