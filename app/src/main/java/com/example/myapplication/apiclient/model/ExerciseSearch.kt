package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable

data class ExerciseSearch(val text: String, val link: String?, val questions: Array<Question>) :
    Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExerciseSearch

        if (text != other.text) return false
        if (link != other.link) return false
        if (!questions.contentEquals(other.questions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + (link?.hashCode() ?: 0)
        result = 31 * result + questions.contentHashCode()
        return result
    }

    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString(),
        source.readArray(ClassLoader.getSystemClassLoader()) as Array<Question>
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeString(link)
        writeArray(questions)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ExerciseSearch> =
            object : Parcelable.Creator<ExerciseSearch> {
                override fun createFromParcel(source: Parcel): ExerciseSearch =
                    ExerciseSearch(source)

                override fun newArray(size: Int): Array<ExerciseSearch?> = arrayOfNulls(size)
            }
    }
}