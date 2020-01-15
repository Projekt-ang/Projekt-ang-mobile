package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Sentence() : Parcelable {
    @SerializedName("polishSentence")
    var polishSentence: String? = null

    @SerializedName("englishSentence")
    var englishSentence: String? = null

    @SerializedName("id")
    var id : Int = 0

    constructor(parcel: Parcel) : this() {
        polishSentence = parcel.readString()
        englishSentence = parcel.readString()
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(polishSentence)
        parcel.writeString(englishSentence)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sentence> {
        override fun createFromParcel(parcel: Parcel): Sentence {
            return Sentence(parcel)
        }

        override fun newArray(size: Int): Array<Sentence?> {
            return arrayOfNulls(size)
        }
    }
}
