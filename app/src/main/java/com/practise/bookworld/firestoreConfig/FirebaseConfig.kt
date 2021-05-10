package com.practise.bookworld.firestoreConfig

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.practise.bookworld.models.Book
import com.practise.bookworld.ui.activities.AddBookActivity
import com.practise.bookworld.ui.activities.DetailsActivity
import com.practise.bookworld.ui.fragments.HomeFragment
import com.practise.bookworld.ui.fragments.MyBooksFragment
import com.practise.bookworld.utils.Constants

class FirebaseConfig {

    private val firestoreObject = FirebaseFirestore.getInstance()// object to access the data from firebase databases

    fun getAllBooks(fragment: Fragment){
        firestoreObject.collection("Books")
            .whereEqualTo("book_id","1233")
            .get()
            .addOnSuccessListener { doc ->
                Log.e("list123",doc.documents.toString())
                val booksList:ArrayList<Book> = ArrayList()

                for(i in doc.documents){
                    val book = i.toObject(Book::class.java)
                    book!!.book_id = i.id
                    booksList.add(book)

                    when(fragment){
                        is MyBooksFragment ->{
                            fragment.onFetchingBooksFromDatabase(booksList)
                        }
                        is HomeFragment ->{
                            fragment.onFetchingBooksFromDatabase(booksList)
                        }
                    }
                }
             }
    }

    fun uploadCoverPhoto(activity: Activity, imageUri: Uri?,type:String,name:String){

        val reference:StorageReference = FirebaseStorage.getInstance().reference.child(
                name+System.currentTimeMillis()+"."+Constants.fileExtention(imageUri,activity)
        )

        reference.putFile(imageUri!!)
                .addOnSuccessListener { taskSnapshot ->

                    taskSnapshot.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener { uri ->

                                when(activity){
                                    is AddBookActivity->{
                                        Log.i("res1","Success")
                                        activity.uploadDetailsToDatabase(uri.toString())
                                    }
                                }
                            }
                }
                .addOnFailureListener{exception->

                }
    }

    fun uploadBookDetails(activity: AddBookActivity, bookDetails: Book) {
        firestoreObject
            .collection("Books")
            .document()
            .set(bookDetails,SetOptions.merge())
            .addOnSuccessListener{

                activity.onUpdatingBookDetails()
            }
    }

    fun deleteABook(fragment: Fragment,bookId:String) {
        firestoreObject.collection("Books")
                .document(bookId)
                .delete()
                .addOnSuccessListener {
                    when (fragment) {
                        is MyBooksFragment -> fragment.deleteSuccessMessage()
                    }
                }
                .addOnFailureListener {

                    when (fragment) {

                        is MyBooksFragment -> {
                            fragment.hideProgressDialog()
                            fragment.deleteFailMessage()
                        }
                    }
                }
    }

    fun getBookDetails(activity:DetailsActivity,book_id:String){
        firestoreObject.collection("Books")
            .document(book_id)
            .get()
            .addOnSuccessListener { doc->
                Log.e(activity.javaClass.simpleName,doc.toString())
                val book = doc.toObject(Book::class.java)
                if (book != null) {
                    activity.onFetchingBookDetails(book)
                }
            }
            .addOnFailureListener { e->
                Log.e(activity.javaClass.simpleName,"Error while fetching book details")
            }
    }
}
