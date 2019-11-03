package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.example.myapplication.R

data class ReadingWithTest(val text: String, val link: String?, val questions: Array<Question>) :
    Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReadingWithTest

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

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeString(link)
        writeInt(questions.size)
        writeTypedArray(questions, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ReadingWithTest> =
            object : Parcelable.Creator<ReadingWithTest> {
                override fun createFromParcel(source: Parcel): ReadingWithTest {
                    val text = source.readString()!!
                    val link = source.readString()
                    val length = source.readInt()
                    val questions: Array<Question> = Question.CREATOR.newArray(length)
                    source.readTypedArray(questions, Question.CREATOR)
                    return ReadingWithTest(text,link,questions)
                }

                override fun newArray(size: Int): Array<ReadingWithTest?> = arrayOfNulls(size)
            }
    }
}