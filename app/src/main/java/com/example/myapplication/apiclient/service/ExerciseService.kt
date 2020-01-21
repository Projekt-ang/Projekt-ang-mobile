package com.example.myapplication.apiclient.service

import com.example.myapplication.apiclient.model.*
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ExerciseService {
    @GET("readingVideoTests")
    fun getAllRedingVideoTests(): Call<ReadingVideoTestAllResponseEmbedded>
    @GET("readingVideoTests/{id}")
    fun getReadingVideoTest(@Path("id") id: Int): Call<ReadingVideoTest>

    @GET("sentences")
    fun getAllSentences(): Call<SentenceAllResponseEmbedded>
    @GET("sentences/{id}")
    fun getSentence(@Path("id") id: Int): Call<Sentence>

    @GET("glossaries")
    fun getAllGlossaries(): Call<GlossarieAllResponseEmbedded>
    @GET("glossaries/{id}")
    fun getGlossarie(@Path("id") id: Int): Call<Glossarie>
}
