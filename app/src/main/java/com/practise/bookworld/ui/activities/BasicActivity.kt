package com.practise.bookworld.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.practise.bookworld.R
import kotlinx.android.synthetic.main.progress_dialog.*

open class BasicActivity : AppCompatActivity() {

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
    }

    fun displayProgressDialog(message:String){
        dialog = Dialog(this)
        dialog.setContentView(R.layout.progress_dialog)
        dialog.progress_bar_text.text = message
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun hideProgressBar(){
        dialog.dismiss()
    }

    fun displaySnackBar(message:String,errorMessage:Boolean){

        val sBar = Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)

        val sBarView = sBar.view

        if(errorMessage){
            sBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BasicActivity,R.color.SnackBarError
                )
            )
        }else{
            sBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BasicActivity,R.color.SnackBarSuccess
                )
            )
        }
        sBar.show()
    }
}