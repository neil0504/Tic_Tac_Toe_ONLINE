package com.example.tic_tac_toe_online.Temp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tic_tac_toe_online.R

class BlogRecyclerViewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<Blog> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is BlogViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(blogList: List<Blog>){
        items = blogList as ArrayList<Blog>
    }
    class BlogViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val blogImage: ImageView = itemView.findViewById(R.id.blog_image)
        val blog_title: TextView = itemView.findViewById(R.id.blog_title)
        val blog_author: TextView = itemView.findViewById(R.id.blog_author)

        fun bind(blog: Blog){
            blog_title.text = blog.title
            blog_author.text = blog.username

            val requestOption =  RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOption)
                .load(blog.iamge)
                .into(blogImage)

        }
    }
}