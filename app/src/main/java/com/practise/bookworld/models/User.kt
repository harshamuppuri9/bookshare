package com.practise.bookworld.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
* Data model to access details of the book
 */
@Parcelize
data class User(
    var user_id:String="",
    val firstName:String="",
    val lastName:String="",
    val email:String="",
    val mobile :String="",
    val image:String="",
    val city:String=""
) : Parcelable