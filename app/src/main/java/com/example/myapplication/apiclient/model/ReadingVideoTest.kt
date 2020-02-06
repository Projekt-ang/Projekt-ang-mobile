package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ReadingVideoTestAllResponseEmbedded() {
    @SerializedName("_embedded")
    var embedded: ReadingVideoTestAllResponse? = null
}

class ReadingVideoTestAllResponse() {
    @SerializedName("readingVideoTests")
    var embedded: Array<ReadingVideoTest>? = null
}

class ReadingVideoTest() : Parcelable {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("text")
    var text: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("link")
    var link: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        text = parcel.readString()
        name = parcel.readString()
        link = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(text)
        parcel.writeString(name)
        parcel.writeString(link)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReadingVideoTest> {
        override fun createFromParcel(parcel: Parcel): ReadingVideoTest {
            return ReadingVideoTest(parcel)
        }

        override fun newArray(size: Int): Array<ReadingVideoTest?> {
            return arrayOfNulls(size)
        }
    }

}
