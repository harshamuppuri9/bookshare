package com.practise.bookworld.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView(context: Context,attrs:AttributeSet):AppCompatTextView(context,attrs) {

    init{
        addFont()
    }

    private fun addFont(){

        val typeface: Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Bold.ttf")
        setTypeface(typeface)
    }

}
