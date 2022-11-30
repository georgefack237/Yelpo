package com.health13.yelpo.data.repository.network

import com.health13.yelpo.utils.YELPConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkInstance {

    val api: YelpService by lazy {
        Retrofit.Builder()
            .baseUrl(YELPConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YelpService::class.java)
    }
}