package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Question() : Parcelable {
    @SerializedName("question")
    var text: String? = null

    @SerializedName("answers")
    var answers: List<Answer>? = null

    constructor(parcel: Parcel) : this() {
        text = parcel.readString()
        answers = parcel.createTypedArrayList(Answer)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text)
        parcel.writeTypedList(answers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}
