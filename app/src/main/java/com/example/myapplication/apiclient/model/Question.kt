package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable

data class Question(val text: String, val correctAnswer: Int, val answers: Array<String>) :
    Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (text != other.text) return false
        if (correctAnswer != other.correctAnswer) return false
        if (!answers.contentEquals(other.answers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + correctAnswer
        result = 31 * result + answers.contentHashCode()
        return result
    }

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readInt(),
        source.createStringArray()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeInt(correctAnswer)
        writeStringArray(answers)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Question> = object : Parcelable.Creator<Question> {
            override fun createFromParcel(source: Parcel): Question = Question(source)
            override fun newArray(size: Int): Array<Question?> = arrayOfNulls(size)
        }
    }
}