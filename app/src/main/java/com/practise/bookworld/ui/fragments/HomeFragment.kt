package com.practise.bookworld.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestoreSettings
import com.practise.bookworld.R
import com.practise.bookworld.firestoreConfig.FirebaseConfig
import com.practise.bookworld.models.Book
import com.practise.bookworld.ui.Adapters.HomePageBooksAdapter
import com.practise.bookworld.ui.Adapters.MyBooksAdapter
import com.practise.bookworld.ui.activities.AddBookActivity

class HomeFragment : Fragment() {

    private lateinit var homePageBooksView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homePageBooksView = inflater.inflate(R.layout.fragment_home, container, false)
        return homePageBooksView
    }

    override fun onResume() {
        super.onResume()
        getBooksListFromFirebaseConfig()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_book_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val item_id = item.itemId
        when(item_id){
            R.id.action_add_book -> {
                startActivity(Intent(activity, AddBookActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onFetchingBooksFromDatabase(HomePageBooksList:ArrayList<Book>){
        for(i in HomePageBooksList){
            Log.i("Book name",i.book_title)
        }

            var recyclerView =  homePageBooksView.findViewById<RecyclerView>(R.id.homepage_items_recylcerView)
            var textView =  homePageBooksView.findViewById<TextView>(R.id.home_page_books_textView)
            if(HomePageBooksList.size > 0){
                recyclerView.visibility = View.VISIBLE
                textView.visibility = View.GONE

                recyclerView.layoutManager = GridLayoutManager(activity,3)
                recyclerView.setHasFixedSize(true)

                val items = HomePageBooksAdapter(requireActivity(),HomePageBooksList,this@HomeFragment)
                recyclerView.adapter = items
            }else{
                recyclerView.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }

    }
    private fun getBooksListFromFirebaseConfig(){
        FirebaseConfig().getAllBooks(this)
    }
}