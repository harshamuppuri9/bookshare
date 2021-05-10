package com.practise.bookworld.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
* Data model to access details of the book
 */
@Parcelize
data class Book(
    var book_id:String="",
    val book_title:String="",
    val book_description:String="",
    val book_ISBN:String="",
    val book_cover :String="",
    val user_id:String="",
) : Parcelable
