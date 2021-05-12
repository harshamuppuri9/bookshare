package com.practise.bookworld.ui.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practise.bookworld.R
import com.practise.bookworld.models.Book
import com.practise.bookworld.ui.activities.DetailsActivity
import com.practise.bookworld.ui.fragments.MyBooksFragment
import com.practise.bookworld.utils.ImageLoader
import kotlinx.android.synthetic.main.book_list_layout.view.*

open class MyBooksAdapter(
        private var context:Context,
        private var list:ArrayList<Book>,
        private var fragment: MyBooksFragment
    ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyBooksViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.book_list_layout,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]
        Log.i("1234",list.toString())
        if(holder is MyBooksViewHolder){
            ImageLoader(context).loadPhoto(model.book_cover,holder.itemView.my_book_image)

            holder.itemView.findViewById<TextView>(R.id.my_book_name).text = model.book_title
            holder.itemView.findViewById<TextView>(R.id.my_book_author).text = model.book_author

            holder.itemView.findViewById<ImageView>(R.id.my_book_delete).setOnClickListener{
                   fragment.deleteProduct(model.book_id
                   )
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(context,DetailsActivity::class.java)
                intent.putExtra("book_id",model.book_id)

                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyBooksViewHolder(view: View):RecyclerView.ViewHolder(view)

}