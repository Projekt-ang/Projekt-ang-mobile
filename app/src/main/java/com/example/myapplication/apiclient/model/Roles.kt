package com.example.myapplication.apiclient.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RoleResponseEmbedded() {
    @SerializedName("_embedded")
    var embedded: RoleResponse? = null
}

class RoleResponse() {
    @SerializedName("roles")
    var embedded: Array<Role>? = null
}

class Role() : Parcelable {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
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

    companion object CREATOR : Parcelable.Creator<Role> {
        override fun createFromParcel(parcel: Parcel): Role {
            return Role(parcel)
        }

        override fun newArray(size: Int): Array<Role?> {
            return arrayOfNulls(size)
        }
    }

}
