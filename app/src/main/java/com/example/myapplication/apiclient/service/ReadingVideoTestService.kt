package com.example.myapplication.apiclient.service

import com.example.myapplication.apiclient.model.ReadingVideoTest
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ReadingVideoTestService {
    @GET("readingVideoTests")
    fun getAll(): Call<List<ReadingVideoTest>>
    @GET("readingVideoTests/{id}")
    fun getReadingVideoTest(@Path("id") id: Int): Call<ReadingVideoTest>
}
