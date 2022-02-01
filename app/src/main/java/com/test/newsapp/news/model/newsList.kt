package com.test.newsapp.news.model

import android.os.Parcel
import android.os.Parcelable

data class newsList(val title : String?,val description : String?,val image_url : String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(image_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<newsList> {
        override fun createFromParcel(parcel: Parcel): newsList {
            return newsList(parcel)
        }

        override fun newArray(size: Int): Array<newsList?> {
            return arrayOfNulls(size)
        }
    }

}
