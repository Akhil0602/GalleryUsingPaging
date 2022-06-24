package com.example.gallery_paging

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.net.URL

class MyPagingAdapter(var cont:Context): PagingDataAdapter<URLs, MyPagingAdapter.URLViewHolder>(URLComparator) {

    override fun onBindViewHolder(holder: URLViewHolder, position: Int) {
        val ele = getItem(position)!!
      //  Glide.with(cont).load(MainActivity.base+"uploads/gallery/images/a2126a162dff8737dad2066e9f4b7b54.jpeg").into(holder.view)
        Glide.with(cont).load(MainActivity.base+ele.getURL()).into(holder.view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): URLViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.picture, parent, false)
        Log.w("Create","Yes")
        return URLViewHolder(v)


       /* val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterMovieBinding.inflate(inflater, parent, false)
        return URLViewHolder(binding)*/
    }

    class URLViewHolder(val itemView: View): RecyclerView.ViewHolder(itemView) {
        var view=itemView.findViewById<ImageView>(R.id.image)
    }

    object URLComparator: DiffUtil.ItemCallback<URLs>() {
        override fun areItemsTheSame(oldItem: URLs, newItem: URLs): Boolean {
            // Id is unique.
          //  return false
            return oldItem.getID() == newItem.getID()
        }

        override fun areContentsTheSame(oldItem: URLs, newItem: URLs): Boolean {
          //  return false
            return oldItem.getURL() == newItem.getURL()
        }
    }
}