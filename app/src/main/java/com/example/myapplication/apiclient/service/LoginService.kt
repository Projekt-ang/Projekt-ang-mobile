package com.example.myapplication.apiclient.service

import com.example.myapplication.apiclient.model.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*


interface LoginService {
    @POST("/api/authenticate")
    fun authenticate(@Body body: JsonObject): Call<JWTToken>
}
