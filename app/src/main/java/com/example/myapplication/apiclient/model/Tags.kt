package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class TagsResponseEmbedded() {
    @SerializedName("_embedded")
    var embedded: TagsResponse? = null
}

class TagsResponse() {
    @SerializedName("tags")
    var embedded: Array<Tags>? = null
}

class Tags() : Parcelable {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("text")
    var text: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        text = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tags> {
        override fun createFromParcel(parcel: Parcel): Tags {
            return Tags(parcel)
        }

        override fun newArray(size: Int): Array<Tags?> {
            return arrayOfNulls(size)
        }
    }

}
