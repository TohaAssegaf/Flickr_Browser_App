package com.example.flickrbrowserapp

import android.os.Parcel
import android.os.Parcelable


class Photo(var display_title:String, var reviewer:String, var critics_pick:String, var link:String, var query:String, var image:String): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!) {
    }

    override fun toString(): String {
        return "Photo(display_title='$display_title', reviewer='$reviewer', critics_pick='$critics_pick', link='$link', query='$query', image='$image')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(display_title)
        parcel.writeString(reviewer)
        parcel.writeString(critics_pick)
        parcel.writeString(link)
        parcel.writeString(query)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }
}