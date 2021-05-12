package com.practise.bookworld.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practise.bookworld.R
import com.practise.bookworld.firestoreConfig.FirebaseConfig
import com.practise.bookworld.models.Book
import com.practise.bookworld.ui.Adapters.MyBooksAdapter
import com.practise.bookworld.ui.activities.AddBookActivity
import com.practise.bookworld.ui.activities.ProfileActivity
import com.practise.bookworld.ui.activities.RegisterActivity

class MyBooksFragment : ProgressFragment() {

    private lateinit var myBooksView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
         myBooksView = inflater.inflate(R.layout.fragment_mybook, container, false)
        return myBooksView
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
            R.id.settings -> {
                startActivity(Intent(activity, ProfileActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onFetchingBooksFromDatabase(booksList:ArrayList<Book>){

        var recyclerView =  myBooksView.findViewById<RecyclerView>(R.id.my_books_items_recylcerView)
        var textView =  myBooksView.findViewById<TextView>(R.id.my_books_items_textView)
        if(booksList.size > 0){
            recyclerView.visibility = View.VISIBLE
            textView.visibility = View.GONE

            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.setHasFixedSize(true)

            val items = MyBooksAdapter(requireActivity(),booksList,this@MyBooksFragment)
            recyclerView.adapter = items
        }else{
            recyclerView.visibility = View.GONE
            textView.visibility = View.VISIBLE
        }
    }

    private fun getBooksListFromFirebaseConfig(){
        FirebaseConfig().getAllBooks(this)
    }

    fun deleteProduct(bookId: String) {

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Delete Book")
        builder.setMessage("Are you sure you want to delete?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes"){DialogInterface,_->
            displayProgressDialog("In progress...")
            FirebaseConfig().deleteABook(this@MyBooksFragment,bookId)
            DialogInterface.dismiss()
        }
        builder.setNegativeButton("No"){DialogInterface,_->
            DialogInterface.dismiss()
        }

        val alertDialog:AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun deleteSuccessMessage(){
        hideProgressDialog()
        Toast.makeText(requireActivity(),"Deleted Successfully",Toast.LENGTH_SHORT)
        FirebaseConfig().getAllBooks(this)
    }

    fun deleteFailMessage(){
        Toast.makeText(requireActivity(),"Failed to delete the book, try again later",Toast.LENGTH_SHORT)
    }
}