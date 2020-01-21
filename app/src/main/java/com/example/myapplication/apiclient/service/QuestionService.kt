package com.example.myapplication.apiclient.service

import com.example.myapplication.apiclient.model.AnswersResponseEmbedded
import com.example.myapplication.apiclient.model.Questions
import com.example.myapplication.apiclient.model.QuestionsResponseEmbedded
import com.example.myapplication.apiclient.model.ReadingVideoTest
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface QuestionService {
    @GET("questions/{id}/answers")
    fun getAnswers(@Path("id") id: Int): Call<AnswersResponseEmbedded>
}
