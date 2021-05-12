package com.practise.bookworld.ui.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practise.bookworld.R
import com.practise.bookworld.models.Book
import com.practise.bookworld.ui.activities.DetailsActivity
import com.practise.bookworld.ui.fragments.HomeFragment
import com.practise.bookworld.utils.ImageLoader
import kotlinx.android.synthetic.main.home_book_list_layout.view.*

class HomePageBooksAdapter (
    private var context: Context,
    private var list:ArrayList<Book>,
    private var fragment: HomeFragment
    ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return HomePageBooksViewHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.home_book_list_layout, parent, false
                    )
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val model = list[position]
            Log.i("12345",list.toString())
            if(holder is HomePageBooksViewHolder){
                ImageLoader(context).loadPhoto(model.book_cover,holder.itemView.homePage_image)

                holder.itemView.findViewById<TextView>(R.id.homePage_bookName).text = model.book_title
                holder.itemView.findViewById<TextView>(R.id.homePage_bookAuthor).text = "By: "+ model.book_author

                holder.itemView.setOnClickListener {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("book_id",model.book_id)

                    context.startActivity(intent)
                }
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class HomePageBooksViewHolder(view: View): RecyclerView.ViewHolder(view)

    }