package com.practise.bookworld.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.practise.bookworld.R
import com.practise.bookworld.databinding.ActivityAddBookBinding
import com.practise.bookworld.databinding.ActivityDetailsBinding
import com.practise.bookworld.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_login.*

import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BasicActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerPageLoginBtn.setOnClickListener{
            var intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener { addUser()}
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            try {
                val keyboardManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboardManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun addUser(){
        if(validateInput()){
            displayProgressDialog("Registration is in progress details,please wait...!")
            val userEmail = binding.email.text .toString().trim{it <= ' '}
            val userPassword = binding.email.text .toString().trim{it <= ' '}

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult>{ task ->

                        if(task.isSuccessful){
                            val user :FirebaseUser =  task.result!!.user!!
                            displaySnackBar("Successfully Registered...!!",true)
                            FirebaseAuth.getInstance().signOut()
                            hideProgressBar()
                        }else{
                            hideProgressBar()
                            displaySnackBar(task.exception!!.message.toString(),false)
                        }
                    }
                )

        }
    }

    private fun validateInput():Boolean {
        return when {
           TextUtils.isEmpty(binding.firstname.text .toString().trim{it <= ' '}) -> {
               displaySnackBar("Enter your Firstname",false)
               false
           }

            TextUtils.isEmpty(binding.lastname.text .toString().trim{it <= ' '}) -> {
                displaySnackBar("Enter your Lastname",false)
                false
            }

            TextUtils.isEmpty(binding.email.text .toString().trim{it <= ' '}) -> {
                displaySnackBar("Enter your email",false)
                false
            }

            TextUtils.isEmpty(binding.password.text .toString().trim{it <= ' '}) -> {
                displaySnackBar("Enter your Password",false)
                false
            }

            TextUtils.isEmpty(binding.confirmPassword.text .toString().trim{it <= ' '}) -> {
                displaySnackBar("Re-enter your Password",false)
                false
            }
            binding.password.text.toString().trim{it <= ' '} != binding.confirmPassword.text.toString()
                    .trim{it <= ' '} -> {
                        displaySnackBar("Passwords don't match,please try again",false)
                     false
             }

            else -> {
                true
            }
        }
    }


}