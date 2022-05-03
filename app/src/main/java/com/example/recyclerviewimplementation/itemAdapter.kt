package com.example.recyclerviewimplementation

import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewimplementation.models.Post
import org.w3c.dom.Text
import java.math.BigInteger
import java.security.MessageDigest

class itemAdapter(val context: Context, private val posts: List<Post>):

    RecyclerView.Adapter<itemAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: itemAdapter.ItemViewHolder, position: Int) {
        Log.i("TAG", "This is an adapter")
        val username = posts[position].user?.username as String
//        holder.imageView.setImageResource(posts[position])
        holder.textView.text = posts[position].user?.username
        holder.textDescription.text = posts[position].description
        Glide.with(context).load(posts[position].imageUrl).into(holder.imageView)
        holder.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(posts[position].creationTimeMs)

        Glide.with(context).load(holder.getProfileImageURl(username)).into(holder.imageViewProfile)

    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        val imageView : ImageView = itemView.findViewById(R.id.ivPost)
        val textView : TextView = itemView.findViewById(R.id.tvUsername)
        val textDescription : TextView = itemView.findViewById(R.id.tvDescription)
        val tvRelativeTime : TextView = itemView.findViewById(R.id.tvRelativeTime)
        val imageViewProfile : ImageView = itemView.findViewById(R.id.ivProfileImage)

        fun getProfileImageURl(username: String): String{
            val digest = MessageDigest.getInstance("MD5")
            val hash = digest.digest(username.toByteArray())
            val bigInt = BigInteger(hash)
            val hex = bigInt.abs().toString(16)
            return "https://www.gravatar.com/avatar/$hex?d=identicon";
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemAdapter.ItemViewHolder {
        Log.i("TAG", "this is oncreate ViewHolder")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return ItemViewHolder(view)
    }




    override fun getItemCount(): Int {
        return posts.size
    }

}

















//
//
//package com.example.recyclerviewimplementation
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.recyclerviewimplementation.models.Post
//
//class itemAdapter(private val itemList:List<Post>):
//
//    RecyclerView.Adapter<itemAdapter.ItemViewHolder>() {
//
//    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//
//
//        val imageView : ImageView = itemView.findViewById(R.id.ivPost)
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemAdapter.ItemViewHolder {
//        Log.i("TAG", "this is oncreate ViewHolder")
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
//        return ItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: itemAdapter.ItemViewHolder, position: Int) {
//        Log.i("TAG", "This is an adapter")
////        holder.imageView.setImageResource(itemList[position])
//
//
//
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//
//}