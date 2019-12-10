package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable

data class ReadingWithTestBackup(val text: String, val link: String?, val questionBackups: Array<QuestionBackup>) :
    Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReadingWithTestBackup

        if (text != other.text) return false
        if (link != other.link) return false
        if (!questionBackups.contentEquals(other.questionBackups)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + (link?.hashCode() ?: 0)
        result = 31 * result + questionBackups.contentHashCode()
        return result
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(text)
        writeString(link)
        writeInt(questionBackups.size)
        writeTypedArray(questionBackups, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ReadingWithTestBackup> =
            object : Parcelable.Creator<ReadingWithTestBackup> {
                override fun createFromParcel(source: Parcel): ReadingWithTestBackup {
                    val text = source.readString()!!
                    val link = source.readString()
                    val length = source.readInt()
                    val questionBackups: Array<QuestionBackup> = QuestionBackup.CREATOR.newArray(length)
                    source.readTypedArray(questionBackups, QuestionBackup.CREATOR)
                    return ReadingWithTestBackup(text,link,questionBackups)
                }

                override fun newArray(size: Int): Array<ReadingWithTestBackup?> = arrayOfNulls(size)
            }
    }
}