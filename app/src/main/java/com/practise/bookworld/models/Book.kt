package com.practise.bookworld.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
* Data model to access details of the book
 */
@Parcelize
data class Book(
    val user_id:String="",
    val book_title:String="",
    val book_author:String="",
    val book_ISBN:String="",
    var book_genres:String="",
    var book_language:String="",
    var book_notes:String="",
    var user_lastName:String="",
    val book_cover :String="",
    var book_id:String="",
) : Parcelable
