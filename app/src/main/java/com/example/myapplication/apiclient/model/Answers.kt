package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class AnswersResponseEmbedded() {
    @SerializedName("_embedded")
    var embedded: Answers? = null
}

class Answers() {
    @SerializedName("answers")
    var answers: Array<Answer>? = null
}