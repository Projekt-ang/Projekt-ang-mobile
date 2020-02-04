package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class QuestionsResponseEmbedded() {
    @SerializedName("_embedded")
    var embedded: Questions? = null
}

class Questions() {
    @SerializedName("questions")
    var questions: Array<Question>? = null
}
