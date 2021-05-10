package com.practise.bookworld.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.practise.bookworld.R
import kotlinx.android.synthetic.main.progress_dialog.*

/*
*Fragment to handle progess Dialog
 */
open class ProgressFragment : Fragment() {

    private lateinit var progressDialog: Dialog

    fun displayProgressDialog(messsage:String){

        progressDialog = Dialog(requireActivity())
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.progress_bar_text.text = messsage
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    fun hideProgressDialog(){
        progressDialog.hide()
    }
}