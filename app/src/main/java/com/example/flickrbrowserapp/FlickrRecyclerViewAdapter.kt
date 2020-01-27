package com.example.flickrbrowserapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class FlickrImageViewHolder(v: View):RecyclerView.ViewHolder(v){
    var thumbnail=v.findViewById<ImageView>(R.id.thumbnail)
    var title=v.findViewById<TextView>(R.id.title)
}

class FlickrRecyclerViewAdapter(private var photoList:List<Photo>):RecyclerView.Adapter<FlickrImageViewHolder>() {
    private var TAG="FlickrRecyclerViewAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        //called by the layout manager when it needs a new view
        Log.d(TAG,"onCreate ViewHolder new view requested")
        return  FlickrImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.browse,parent,false))

    }

    override fun getItemCount(): Int {
//        Log.d(TAG,".getItemCount called")
        return if(photoList.isNotEmpty()) photoList.size else 0
    }

    fun loadNewData(newPhotos:List<Photo>){
        photoList=newPhotos
        notifyDataSetChanged()
        Log.d(TAG,notifyDataSetChanged().toString())
    }

    fun getPhoto(position:Int): Photo? {
       return if(photoList.isNotEmpty()) photoList[position] else null

    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        if(photoList.isEmpty()){
            holder.thumbnail.setImageResource(R.drawable.placeholder)
            holder.title.setText(R.string.no_photos_found)
        }else{
            val photoItem=photoList[position]
//        Log.d(TAG,".onBindHolder : ${photoItem.title} --> $position")
            //called by the layout manager when it wants new data in an existing view
            Picasso.with(holder.thumbnail.context)
                .load(photoItem.image)//load an image from url
                .placeholder(R.drawable.placeholder)//yee
                .error(R.drawable.placeholder)
                .into(holder.thumbnail)

            holder.title.text = photoItem.display_title
        }


    }
}