package com.practise.bookworld.utils

import android.content.Context
import android.widget.ImageView
import java.io.IOException
import com.bumptech.glide.Glide

/*
*Object with functions to load and display images using Glide Library
 */
class ImageLoader(val context: Context) {

    //Function to load,scale and display images of the books using the URL or URI
    fun loadPhoto(image:Any, imageView: ImageView){
        try{
            Glide.with(context)
                    .load(image)
                    .centerCrop()
                    .into(imageView)
        }catch (e: IOException){
            e.printStackTrace()
        }
    }


}