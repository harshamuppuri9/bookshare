package com.practise.bookworld.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView2(context: Context, attrs: AttributeSet): AppCompatTextView(context,attrs) {

    init{
        addFont()
    }

    private fun addFont(){

        val typeface: Typeface = Typeface.createFromAsset(context.assets,"Montserrat-Regular.ttf")
        setTypeface(typeface)
    }
}