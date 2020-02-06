package com.example.myapplication.apiclient.service

import com.example.myapplication.apiclient.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ReadingVideoTestService {
    @GET("readingVideoTests")
    fun getAll(): Call<ReadingVideoTestAllResponseEmbedded>
    @GET("readingVideoTests/{id}")
    fun getReadingVideoTest(@Path("id") id: Int): Call<ReadingVideoTest>
    @GET("readingVideoTests/{id}/questions")
    fun getReadingVideoTestQuestions(@Path("id") id: Int): Call<QuestionsResponseEmbedded>
    @GET("readingVideoTests/{id}/tags")
    fun getReadingVideoTestTags(@Path("id") id: Int): Call<TagsResponseEmbedded>
    @GET("readingVideoTests/{id}/roles")
    fun getReadingVideoTestRoles(@Path("id") id: Int): Call<RoleResponseEmbedded>
}
