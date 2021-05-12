package com.practise.bookworld.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.practise.bookworld.R
import com.practise.bookworld.databinding.ActivityDetailsBinding
import com.practise.bookworld.firestoreConfig.FirebaseConfig
import com.practise.bookworld.models.Book
import com.practise.bookworld.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var book_id:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var bookdetails_toolbar:Toolbar  = findViewById<View>(R.id.bookdetails_toolbar) as Toolbar
        setupActionBar(bookdetails_toolbar)
        if(intent.hasExtra("book_id")){
            book_id = intent.getStringExtra("book_id")!!
        }

        getBookDetails()
    }

    private fun setupActionBar(bookdetails_toolbar: Toolbar) {

         setSupportActionBar(bookdetails_toolbar)
        val toolBar = supportActionBar
        if(toolBar != null){
            toolBar.setDisplayHomeAsUpEnabled(true)
            toolBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back)
        }
        bookdetails_toolbar.setNavigationOnClickListener{onBackPressed()}
    }

    private fun getBookDetails(){
        FirebaseConfig().getBookDetails(this,book_id)
    }

    fun onFetchingBookDetails(book: Book){

        //Load Book Cover photo
        ImageLoader(this@DetailsActivity).loadPhoto(
            book.book_cover,
            bookdetails_image
        )

        book_details_title.text = "Title: "+book.book_title
        book_details_author.text ="Author: "+book.book_author
        book_details_ISBN.text = "ISBN: "+book.book_ISBN
        book_details_genres.text = "Genres: "+book.book_genres
        book_details_language.text = "Language: "+book.book_language
        book_details_notes.text = "Notes from user: "+book.book_notes
    }
}