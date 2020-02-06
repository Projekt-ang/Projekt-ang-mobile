package com.example.myapplication.apiclient.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Services {
    companion object {
        fun prepareRetrofit(): Retrofit{
            val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            val retrofit = Retrofit.Builder()
                .baseUrl("http://18.195.242.27:8088/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
            return retrofit
        }

        val retrofit = prepareRetrofit()
        val READING_VIDEO_TEST_SERVICE = retrofit.create(ReadingVideoTestService::class.java)
        val QUESTION_SERVICE = retrofit.create(QuestionService::class.java)
        val EXERCISE_SERVICE = retrofit.create(ExerciseService::class.java)
        val LOGIN_SERVICE = retrofit.create(LoginService::class.java)
    }

}