package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UserAllResponseEmbedded() {
    @SerializedName("_embedded")
    var embedded: UserAllResponse? = null
}

class UserAllResponse() {
    @SerializedName("users")
    var embedded: Array<User>? = null
}


class User() : Parcelable {
    @SerializedName("username")
    var username: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("firstName")
    var name: String? = null

    @SerializedName("id")
    var id : Int = 0

    constructor(parcel: Parcel) : this() {
        username = parcel.readString()
        password = parcel.readString()
        name = parcel.readString()
        id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(name)
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
