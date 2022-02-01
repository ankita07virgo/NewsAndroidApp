package com.test.newsapp.news.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class NewsBase(val status : String,
                    val totalResults : Int,
                    val results : List<resultList>,
                    val nextPage : Int)
{   @Entity(tableName = "news_table")
    data class resultList(@PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") val id:Int,@ColumnInfo(name="title") val title : String?, @ColumnInfo(name="des")  val description : String?, @ColumnInfo(name="image_url") val image_url : String?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(image_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<resultList> {
        override fun createFromParcel(parcel: Parcel): resultList {
            return resultList(parcel)
        }

        override fun newArray(size: Int): Array<resultList?> {
            return arrayOfNulls(size)
        }
    }


}
}
