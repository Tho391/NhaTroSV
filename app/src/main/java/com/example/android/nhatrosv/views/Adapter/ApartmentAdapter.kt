package com.example.android.nhatrosv.views.Adapter

import android.util.Log

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.views.Activity.ApartmentDetailActivity
import com.example.android.nhatrosv.views.Activity.MainActivity
import com.example.android.nhatrosv.views.inflate
import com.example.android.nhatrosv.views.lauchActivity


data class ApartmentAdapter(var mApartments: List<Apartment>) :
    RecyclerView.Adapter<ApartmentAdapter.ApartmentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentViewHolder {
        //val inflatedView = parent.inflate(R.layout.apartment_item, false)
        val inflatedView = parent.inflate(R.layout.apartment_item, false)
        return ApartmentViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mApartments.size
    }

    override fun onBindViewHolder(holder: ApartmentViewHolder, position: Int) {
        val apartment = mApartments[position]

        holder.imageView.setImageBitmap(apartment.listImage?.get(0))
        holder.tvName.text = apartment.name
        holder.tvDate.text = apartment.date.toString()
        holder.tvPrice.text = apartment.price.toString()
        holder.tvDetail.text = apartment.description
    }


    class ApartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var view: View = itemView
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var tvName: TextView = itemView.findViewById(R.id.textView_name)
        var tvDate: TextView = itemView.findViewById(R.id.textView_date)
        var tvPrice: TextView = itemView.findViewById(R.id.textView_price)
        var tvDetail: TextView = itemView.findViewById(R.id.textView_detail)
        var btnDetail: Button = itemView.findViewById(R.id.button_detail)
        var btnMap: Button = itemView.findViewById(R.id.button_map)

        init {
            view.setOnClickListener(this)
            btnDetail.setOnClickListener(this)
            btnMap.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            //Log.d("RecyclerView", "CLICK!")
            when (p0?.id) {
                R.id.button_detail -> {
                    p0.context.lauchActivity<ApartmentDetailActivity> { }
                }
                R.id.button_map -> {
                    p0.context.lauchActivity<MainActivity> {  }
                }
                else -> {
                    Log.d("RecyclerView", "ViewCLICK!")
                }

            }
        }
    }

}