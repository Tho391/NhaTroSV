package com.example.android.nhatrosv.views.adapter

import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.utils.inflate
import com.example.android.nhatrosv.utils.launchActivity
import com.example.android.nhatrosv.views.activity.ApartmentDetailActivity
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList


data class ApartmentAdapter(var mApartments: List<Apartment>) :
    RecyclerView.Adapter<ApartmentAdapter.ApartmentViewHolder>(), Filterable {

    var filterApartments: List<Apartment>

    init {
        filterApartments = mApartments
    }

    companion object {
        private val charA = charArrayOf(
            'à', 'á', 'ạ', 'ả', 'ã',  // 0-&gt;16
            'â', 'ầ', 'ấ', 'ậ', 'ẩ', 'ẫ', 'ă', 'ằ', 'ắ', 'ặ', 'ẳ', 'ẵ'
        ) // a,// ă,// â

        private val charE = charArrayOf(
            'ê', 'ề', 'ế', 'ệ', 'ể', 'ễ',  // 17-&gt;27
            'è', 'é', 'ẹ', 'ẻ', 'ẽ'
        ) // e

        private val charI = charArrayOf('ì', 'í', 'ị', 'ỉ', 'ĩ') // i 28-&gt;32

        private val charO = charArrayOf(
            'ò', 'ó', 'ọ', 'ỏ', 'õ',  // o 33-&gt;49
            'ô', 'ồ', 'ố', 'ộ', 'ổ', 'ỗ',  // ô
            'ơ', 'ờ', 'ớ', 'ợ', 'ở', 'ỡ'
        ) // ơ

        private val charU = charArrayOf(
            'ù', 'ú', 'ụ', 'ủ', 'ũ',  // u 50-&gt;60
            'ư', 'ừ', 'ứ', 'ự', 'ử', 'ữ'
        ) // ư

        private val charY = charArrayOf('ỳ', 'ý', 'ỵ', 'ỷ', 'ỹ') // y 61-&gt;65

        private val charD = charArrayOf('đ', ' ') // 66-67

        var charact = String(charA, 0, charA.size) +
                String(charE, 0, charE.size) +
                String(charI, 0, charI.size) +
                String(charO, 0, charO.size) +
                String(charU, 0, charU.size) +
                String(charY, 0, charY.size) +
                String(charD, 0, charD.size)
    }

    private fun getAlterChar(pC: Char): Char {
        if (pC.toInt() == 32) {
            return ' '
        }

        val tam = pC.toLowerCase() // Character.toLowerCase(pC);

        var i = 0
        while (i < charact.length && charact[i] != tam) {
            i++
        }

//        if (i < 0 || i > 67)
//            return pC

        return when (i) {
            in 0..16 -> 'a'
            in 17..27 -> 'e'
            in 28..32 -> 'i'
            in 33..49 -> 'o'
            in 50..60 -> 'u'
            in 61..65 -> 'y'
            66 -> 'd'
            else -> pC
        }
    }

    fun convertString(pStr: String): String {
        var convertString = pStr.toLowerCase(Locale.getDefault())
        for (i in convertString) {
            if (i.toInt() !in 97..122) {
                val tam1 = getAlterChar(i)
                if (i.toInt() != 32)
                    convertString = convertString.replace(i, tam1)
            }
        }
        return convertString
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
        holder.tvAddress.text = apartment.street + " " + apartment.district
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvArea.text =
                Html.fromHtml(apartment.area.toString() + " m<sup><small>2</small></sup>", 1)
        } else
            holder.tvArea.text = apartment.area.toString() + " m2"
        Picasso.get()
            .load(Uri.parse(apartment.imageURl.toString()))
            .into(holder.imageView)

        holder.btnDetail.setOnClickListener {
            it.context.launchActivity<ApartmentDetailActivity> {
                this.putExtra("apartment", apartment)
            }
        }
        holder.btnMap.setOnClickListener {

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
                                            + row.street!!.toLowerCase()
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