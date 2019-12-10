package com.example.myapplication.apiclient.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Services {
    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://18.195.242.27:8088/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val READING_VIDEO_TEST_SERVICE = retrofit.create(ReadingVideoTestService::class.java)
    }
}