package com.example.android.nhatrosv.views.Fragment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Comment
import com.example.android.nhatrosv.models.Coordinate
import com.example.android.nhatrosv.views.Activity.ApartmentDetailActivity
import com.example.android.nhatrosv.views.Adapter.ApartmentAdapter
import com.example.android.nhatrosv.views.decodeBitmap
import com.example.android.nhatrosv.views.launchActivity


class HomeFragment : Fragment() {
    lateinit var toolbar: Toolbar

    lateinit var recyclerView: RecyclerView
    lateinit var mApartmentAdapter: ApartmentAdapter
    lateinit var mApartments: List<Apartment>

//    companion object{
//        fun newInstance(): HomeFragment {
//            return HomeFragment()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
        if (activity is AppCompatActivity)
            (activity as AppCompatActivity).setSupportActionBar(toolbar)

        recyclerView = rootView.findViewById(R.id.recycler_view)


        //region sample data
        //create data
        val coordinate = Coordinate("toado1", 1, 1)
        val comment = Comment("id1")
        val image: Bitmap = rootView.context.decodeBitmap(R.mipmap.nhatro)!!
        val a1 = Apartment(
            "1", "Nhà trọ 1", "21/10/2019", "0346563548",
            2000000, true, "Mô tả: phòng trọ nè..................",
            listOf(comment), "địa chỉ nè", listOf(image), coordinate
        )
        val a2 = Apartment(
            "1", "Nhà trọ 1", "21/10/2019", "0346563548",
            2000000, true, "Mô tả: phòng trọ nè..................",
            listOf(comment), "địa chỉ nè", listOf(image), coordinate
        )
        val a3 = Apartment(
            "1", "Nhà trọ 1", "21/10/2019", "0346563548",
            2000000, true, "Mô tả: phòng trọ nè..................",
            listOf(comment), "địa chỉ nè", listOf(image), coordinate
        )
        val a4 = Apartment(
            "1", "Nhà trọ 1", "21/10/2019", "0346563548",
            2000000, true, "Mô tả: phòng trọ nè..................",
            listOf(comment), "địa chỉ nè", listOf(image), coordinate
        )
        val a5 = Apartment(
            "1", "Nhà trọ 1", "21/10/2019", "0346563548",
            2000000, true, "Mô tả: phòng trọ nè..................",
            listOf(comment), "địa chỉ nè", listOf(image), coordinate
        )

        //endregion

        mApartments = listOf(a1, a2, a3, a4, a5)
        mApartmentAdapter = ApartmentAdapter(mApartments)
        recyclerView.adapter = mApartmentAdapter

        val linearLayoutManager =
            LinearLayoutManager(rootView.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager


        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val mSearch = menu.findItem(R.id.action_search)
        val mSearchView = mSearch.actionView as SearchView

        super.onCreateOptionsMenu(menu, inflater)

    }
}