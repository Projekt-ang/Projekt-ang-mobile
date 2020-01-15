package com.example.myapplication.apiclient.service

import com.example.myapplication.apiclient.model.ReadingVideoTest
import com.example.myapplication.apiclient.model.Sentence
import com.example.myapplication.apiclient.model.Glossarie
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ExerciseService {
    @GET("readingVideoTests")
    fun getAllRedingVideoTests(): Call<List<ReadingVideoTest>>
    @GET("readingVideoTests?page=0")
    fun getPageReadingVideoTests(): Call<List<ReadingVideoTest>>
    @GET("readingVideoTests/{id}")
    fun getReadingVideoTest(@Path("id") id: Int): Call<ReadingVideoTest>

    @GET("sentences")
    fun getAllSentences(): Call<List<Sentence>>
    @GET("sentences?page=0")
    fun getPageSentences(): Call<List<Sentence>>
    @GET("sentences/{id}")
    fun getSentence(@Path("id") id: Int): Call<Sentence>

    @GET("glossaries")
    fun getAllGlossaries(): Call<List<Glossarie>>
    @GET("glossaries?page=0")
    fun getPageGlossaries(): Call<List<Glossarie>>
    @GET("glossaries/{id}")
    fun getGlossarie(@Path("id") id: Int): Call<Glossarie>
}
