package com.practise.bookworld.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.practise.bookworld.R
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.practise.bookworld.databinding.ActivityAddBookBinding
import com.practise.bookworld.firestoreConfig.FirebaseConfig
import com.practise.bookworld.models.Book
import com.practise.bookworld.utils.Constants
import com.practise.bookworld.utils.ImageLoader
import java.io.IOException

class AddBookActivity : AppCompatActivity(),View.OnClickListener {

    private var selectedImageUri: Uri? = null
    private var updateUri:String=""
    private lateinit var binding: ActivityAddBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toolbar:Toolbar  = findViewById<View>(R.id.add_book_toolbar) as Toolbar
         setupActionBar(toolbar)
        binding.updateBookImage.setOnClickListener(this)
        binding.addBookSubmitBtn.setOnClickListener(this)
    }

    private fun setupActionBar(add_book_toolbar:Toolbar){

    setSupportActionBar(add_book_toolbar)
        val toolBar = supportActionBar
        if(toolBar != null){
            toolBar.setDisplayHomeAsUpEnabled(true)
            toolBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back)
        }
    add_book_toolbar.setNavigationOnClickListener{onBackPressed()}
    }

    override fun onClick(v: View?) {

        if(v != null){
            when(v.id){
                R.id.update_book_image ->{

                    if(ContextCompat.checkSelfPermission(this,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED ){
                        Constants.selectImage(this@AddBookActivity)

                    }else{

                        //Request must be granted to the application
                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),Constants.READ_LOCALSTORAGE_PERMISSION_CODE)
                    }
                }

                R.id.add_book_submit_btn ->{

                    if(selectedImageUri != null){
                       FirebaseConfig().uploadCoverPhoto(this@AddBookActivity,selectedImageUri,Constants.Book_Photo,"test")
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults)
        if(requestCode == Constants.READ_LOCALSTORAGE_PERMISSION_CODE){
            Constants.selectImage(this@AddBookActivity)

        }else{
            Toast.makeText(this,"Permission Denied.Access should be granted from settings",Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK
                && data!!.data != null) {

            selectedImageUri = data!!.data //Get Data Uri

            //Replace the add image logo to edit
            var image:ImageView = findViewById<View>(R.id.update_book_image) as ImageView
            image.setImageDrawable(
                    ContextCompat.getDrawable(
                    this@AddBookActivity,R.drawable.ic_baseline_arrow_back
                    )
            )
            try{
                ImageLoader(this@AddBookActivity)
                        .loadCoverPhoto(selectedImageUri!!,findViewById(R.id.add_book_image))
            }catch (e:IOException){
                e.printStackTrace()
            }
        }
    }

    fun uploadDetailsToDatabase(uri: String) {
        updateUri = uri

        val book = Book(
           "1233",
            binding.addBookTitle.text.toString().trim{it <= ' '},
            binding.addBookDescription.text.toString().trim{it <= ' '},
            binding.addBookISBN.text.toString().trim{it <= ' '},
            updateUri,
                "17474"
        )

        FirebaseConfig().uploadBookDetails(this@AddBookActivity,book)
    }

     fun onUpdatingBookDetails() {
         Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
    }
}