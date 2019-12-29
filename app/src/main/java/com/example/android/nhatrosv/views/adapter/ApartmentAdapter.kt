package com.example.android.nhatrosv.views.adapter

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.MainScreen
import com.example.android.nhatrosv.utils.CharacterTransform.convertString
import com.example.android.nhatrosv.utils.inflate
import com.example.android.nhatrosv.utils.launchActivity
import com.example.android.nhatrosv.views.activity.ApartmentDetailActivity
import com.example.android.nhatrosv.views.activity.MainActivity
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


data class ApartmentAdapter(val context: Context, var mApartments: List<Apartment>) :
    RecyclerView.Adapter<ApartmentAdapter.ApartmentViewHolder>(), Filterable {

    var filterApartments: List<Apartment>

    init {
        filterApartments = mApartments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentViewHolder {
        //val inflatedView = parent.inflate(R.layout.apartment_item, false)
        val inflatedView = parent.inflate(R.layout.apartment_item, false)
        return ApartmentViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return filterApartments.size
    }

    override fun onBindViewHolder(holder: ApartmentViewHolder, position: Int) {
        val apartment = filterApartments[position]

        holder.tvName.text = apartment.name
        holder.tvDate.text = apartment.date
        holder.tvPrice.text = apartment.price.toString()
        holder.tvAddress.text = "${apartment.address} ${apartment.district}"

        holder.tvArea.text = "${apartment.area.toString()} m\u00B2"
        Picasso.get()
            .load(Uri.parse(apartment.imageURl.toString()))
            .into(holder.imageView)

        holder.btnDetail.setOnClickListener {
            it.context.launchActivity<ApartmentDetailActivity> {
                this.putExtra("apartmentId", apartment.id)
            }
        }
        holder.btnMap.setOnClickListener {
            //this.putExtra()

            if (context.javaClass == MainActivity::class.java) {
                (context as MainActivity).scrollToScreen(MainScreen.MAP, apartment.id!!)
            }

        }
    }

    fun updateApartments(apartments: List<Apartment>?) {
        if (apartments != null) {
            mApartments = apartments
            filterApartments = apartments
            notifyDataSetChanged()
        }
    }

    fun updateApartments(
        district: String?,
        area: Int?,
        price: Float?
    ) {
        var filterList: List<Apartment> = mApartments
        if (district != null && district != "Tất cả")
            filterList = filterList.filter { it.district == district }
        if (area != null && area > 0)
            filterList = filterList.filter { it.area!! < area }
        if (price != null && price > 0)
            filterList = filterList.filter { it.price!! < price }
        filterApartments = filterList

        notifyDataSetChanged()
    }

    fun clearFilter() {
        filterApartments = mApartments
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString()
                filterApartments =
                    if (charString.isEmpty()) {
                        mApartments
                    } else {
                        val filteredList: ArrayList<Apartment> = ArrayList()

                        for (row in mApartments) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            val address =
                                convertString(
                                    row.district!!.toLowerCase() + " "
                                            + row.address!!.toLowerCase()
                                )
                            val query = convertString(charString.toLowerCase())
                            if (address.contains(query)) {
                                filteredList.add(row)
                            }
                        }
                        filteredList
                    }
                val filterResults = FilterResults()
                filterResults.values = filterApartments
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults
            ) {
                if (filterResults.values != null) {
                    val temp = filterResults.values as List<Apartment>
                    filterApartments = temp
                    notifyDataSetChanged()
                }
            }
        }
    }


    class ApartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view: View = itemView
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var tvName: TextView = itemView.findViewById(R.id.textView_name)
        var tvDate: TextView = itemView.findViewById(R.id.textView_date)
        var tvPrice: TextView = itemView.findViewById(R.id.textView_price)
        var btnDetail: Button = itemView.findViewById(R.id.button_detail)
        var btnMap: Button = itemView.findViewById(R.id.button_map)
        var tvAddress: TextView = itemView.findViewById(R.id.textView_Address)
        var tvArea: TextView = itemView.findViewById(R.id.textView_area)

    }

}