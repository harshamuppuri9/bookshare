package com.practise.bookworld.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText

class CustomButton(context: Context, attrs: AttributeSet): AppCompatButton(context,attrs) {

    init {
        addFont()
    }

    private fun addFont() {

        val typeface: Typeface = Typeface.createFromAsset(context.assets, "gomarice_old_book.ttf")
        setTypeface(typeface)

    }
}