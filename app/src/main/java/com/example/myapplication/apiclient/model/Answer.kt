package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Answer() {
    @SerializedName("answer")
    var text: String? = null

    @SerializedName("correct")
    var correct: Boolean? = null

}