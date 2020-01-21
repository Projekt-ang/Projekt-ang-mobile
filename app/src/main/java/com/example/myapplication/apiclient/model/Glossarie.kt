package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class GlossarieAllResponseEmbedded() {
    @SerializedName("_embedded")
    var embedded: GlossarieAllResponse? = null
}

class GlossarieAllResponse() {
    @SerializedName("glossaries")
    var embedded: Array<Glossarie>? = null
}


class Glossarie() : Parcelable {
    @SerializedName("word")
    var word: String? = null

    @SerializedName("definition")
    var definition: String? = null

    @SerializedName("id")
    var id : Int = 0

    constructor(parcel: Parcel) : this() {
        word = parcel.readString()
        definition = parcel.readString()
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(word)
        parcel.writeString(definition)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Glossarie> {
        override fun createFromParcel(parcel: Parcel): Glossarie {
            return Glossarie(parcel)
        }

        override fun newArray(size: Int): Array<Glossarie?> {
            return arrayOfNulls(size)
        }
    }
}
