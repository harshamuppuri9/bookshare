package com.practise.bookworld.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.practise.bookworld.R
import com.practise.bookworld.firestoreConfig.FirebaseConfig
import com.practise.bookworld.models.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BasicActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPage_registerBtn.setOnClickListener{
            var intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
        }

        resetPassword.setOnClickListener{
            userLogin()
        }

        forgotPassword.setOnClickListener{
            var intent = Intent(this@LoginActivity,ForgotActivity::class.java)
            startActivity(intent)
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            try{
                val keyboardManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboardManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            }catch(e:Exception){
                e.printStackTrace()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun userLogin(){
        if(validateUser()){
            displayProgressDialog("Signing In,please wait...!")
            val userEmail = emailId.text .toString().trim{it <= ' '}
            val userPassword = userPassword.text .toString().trim{it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener{ task ->
                        hideProgressBar()
                        if(task.isSuccessful){

                            val user : FirebaseUser =  task.result!!.user!!
                            displaySnackBar("Successfully Logged In...!!",true)
                            FirebaseConfig().getUserDetails(this@LoginActivity)
                        }else{
                            displaySnackBar(task.exception!!.message.toString(),false)
                        }
                }
        }
    }

    private fun validateUser(): Boolean {
        return when {
            TextUtils.isEmpty(emailId.text.toString().trim { it <= ' ' }) -> {
                displaySnackBar("Enter your Email Id", false)
                false
            }

            TextUtils.isEmpty(userPassword.text.toString().trim { it <= ' ' }) -> {
                displaySnackBar("Enter your Password to login", false)
                false
            }
            else ->  true
        }
    }

    fun onLoginSuccess(user: User) {
        startActivity(Intent(this@LoginActivity,MyBooksActivity
        ::class.java))
    }
}