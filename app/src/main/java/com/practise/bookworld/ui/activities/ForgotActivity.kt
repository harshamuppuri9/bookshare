package com.practise.bookworld.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.practise.bookworld.R
import kotlinx.android.synthetic.main.activity_forgot.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.resetPassword

class ForgotActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        resetPassword.setOnClickListener {

            displayProgressDialog("In progress details,please wait...!")
            val userEmail = userEmail.text .toString().trim{it <= ' '}

            if(userEmail.isNotEmpty()){

                FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail)
                    .addOnCompleteListener{ task ->
                        hideProgressBar()
                        if(task.isSuccessful){

                            displaySnackBar("An Email with link has been sent to your address...!!",true)

                        }else{
                            displaySnackBar(task.exception!!.message.toString(),false)
                        }
                    }
            }else{
                displaySnackBar("Please enter your email id",false)
            }
        }
    }
}