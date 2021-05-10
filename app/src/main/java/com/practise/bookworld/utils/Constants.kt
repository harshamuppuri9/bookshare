package com.practise.bookworld.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap

object Constants {

    const val READ_LOCALSTORAGE_PERMISSION_CODE = 2

    //request code to select image from phone local storage
    const val SELECT_IMAGE_REQUEST_CODE = 2

    const val Book_Photo = "Book_Photo"

    /*
    *Function to select image from phone gallery
     */
    fun selectImage(activity: Activity){
        val imagesFolderIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        Log.i("ping12345","sm vmds vmds vmds v")
        //Display gallery of the phone storage using request code
        activity.startActivityForResult(imagesFolderIntent,SELECT_IMAGE_REQUEST_CODE)
    }

    /*
    *Function to get the file extention of the selected image
     */
    fun fileExtention(uri: Uri?,activity: Activity): String? {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }


}