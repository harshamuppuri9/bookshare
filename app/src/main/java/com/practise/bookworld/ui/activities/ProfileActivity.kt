package com.practise.bookworld.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.practise.bookworld.R
import com.practise.bookworld.databinding.ActivityProfileBinding
import com.practise.bookworld.firestoreConfig.FirebaseConfig
import com.practise.bookworld.models.Book
import com.practise.bookworld.models.User
import com.practise.bookworld.utils.Constants
import com.practise.bookworld.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.IOException

class ProfileActivity : BasicActivity(){
    private var updateUri: Uri? =null
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var toolbar: Toolbar = findViewById<View>(R.id.profile_toolbar) as Toolbar

        setupActionBar(toolbar)
        setUserDetails()


        binding.imageUpdateBtn.setOnClickListener{ updateProfilePicture() }


        binding.updateSubmitBtn.setOnClickListener{updateProfileDetails() }

        binding.LogOutSubmitBtn.setOnClickListener {

            var intent = Intent(this@ProfileActivity,LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }

    private fun updateProfileDetails() {
        var lastName = lastname.text.toString()
        if(updateUri != null){
            displayProgressDialog("Updating..Please wait")
            FirebaseConfig().uploadPhoto(this@ProfileActivity,updateUri,"profile_pic",lastName)
        }
        else {
            displaySnackBar("something went wrong,Please try again later",false)
        }
    }

    private fun updateProfilePicture(){
        if(ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED ){
            Constants.selectImage(this@ProfileActivity)
        }else{

            //Request must be granted to the application
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_LOCALSTORAGE_PERMISSION_CODE)
        }
    }


    private fun setupActionBar(profile_toolbar:Toolbar){

        setSupportActionBar(profile_toolbar)
        val toolBar = supportActionBar
        if(toolBar != null){
            toolBar.setDisplayHomeAsUpEnabled(true)
            toolBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back)
        }
        profile_toolbar.setNavigationOnClickListener{onBackPressed()}
    }

    fun setUserDetails(){
        var ref = this.getSharedPreferences("USER", Context.MODE_PRIVATE)
            firstname.setText(ref.getString("firstName",""))
            lastname.setText(ref.getString("lastName",""))
            email.setText(ref.getString("email",""))
            profile_mobile.setText(ref.getString("mobile",""))
            profile_city.setText(ref.getString("city",""))
        ImageLoader(this@ProfileActivity)
                .loadPhoto(ref.getString("image","")!!,findViewById(R.id.add_profile_image))

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults)
        if(requestCode == Constants.READ_LOCALSTORAGE_PERMISSION_CODE){
            Constants.selectImage(this@ProfileActivity)

        }else{
            Toast.makeText(this,"Permission Denied.Access should be granted from settings", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK
                && data!!.data != null) {

            updateUri = data!!.data //Get image Uri

            //Replace the add image logo to edit
            var image: ImageView = findViewById<View>(R.id.image_updateBtn) as ImageView
            image.setImageDrawable(
                    ContextCompat.getDrawable(
                            this@ProfileActivity,R.drawable.ic_baseline_mode_edit_24
                    )
            )
            try{
                ImageLoader(this@ProfileActivity)
                        .loadPhoto(updateUri!!,findViewById(R.id.add_profile_image))
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    fun uploadDetailsToDatabase(uri: String) {

        var ref = this.getSharedPreferences("USER",Context.MODE_PRIVATE)
        var email =  ref.getString("email","")
        var id = ref.getString("id","")
        val map = HashMap<String,Any>()
        map["firstName"] =  binding.firstname.text.toString().trim{it <= ' '}
        map["lastName"] =  binding.lastname.text.toString().trim{it <= ' '}
        map["mobile"] =  binding.profileMobile.text.toString().trim{it <= ' '}
        map["city"] =  binding.profileCity.text.toString().trim{it <= ' '}
        map["image"] =  uri
        FirebaseConfig().updateUserDetails(this@ProfileActivity,map)
    }

    fun updateSuccess() {
        hideProgressBar()
        displaySnackBar("Updated..!!",true)
    }

    fun updateFailed() {
        hideProgressBar()
        displaySnackBar("Please try again later..!!",false)
    }

}