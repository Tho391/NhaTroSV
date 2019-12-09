package com.example.android.nhatrosv.views.adapter

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.utils.inflate

data class ImageAdapter(var mImages: List<Bitmap>):
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflatedView = parent.inflate(R.layout.image_item, false)
        return ImageViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mImages.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = mImages[position]
        holder.imageView.setImageBitmap(image)

    }

    class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}