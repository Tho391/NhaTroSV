package com.example.android.nhatrosv.views.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Response
import com.example.android.nhatrosv.utils.get
import com.example.android.nhatrosv.utils.getViewModel
import com.example.android.nhatrosv.viewModels.MainActivityViewModel
import com.example.android.nhatrosv.views.activity.OnDataReceivedListener
import com.example.android.nhatrosv.views.adapter.ApartmentAdapter


class HomeFragment : Fragment(), OnDataReceivedListener {

    private val RC_CANCEL: Int = 1001
    private val RC_HOME: Int = 1000
    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView
    lateinit var mApartmentAdapter: ApartmentAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var viewModel: MainActivityViewModel
    var mApartments: List<Apartment> = ArrayList()

    lateinit var sharedPreferences: SharedPreferences
    lateinit var response: Response

    lateinit var mSearch: MenuItem
    lateinit var mSearchView: SearchView

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
        toolbar.title = "Nhà trọ"

        sharedPreferences =
            requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        response = Response(sharedPreferences.get("token", "null"), null)
        viewModel = getViewModel<MainActivityViewModel>()
        recyclerView = rootView.findViewById(R.id.recycler_view)

        mApartmentAdapter = ApartmentAdapter(requireContext(), ArrayList())
        recyclerView.adapter = mApartmentAdapter

        val linearLayoutManager =
            LinearLayoutManager(rootView.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        //loadApartments()
        loadApartment(response.getToken())

        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            loadApartment(response.getToken())
        }

        return rootView
    }

    // GET List of apartment
    private fun loadApartment(token: String) {
        viewModel.getApartments(token).observe(this,
            Observer { response ->
                mApartments = response
                mApartmentAdapter.updateApartments(mApartments)
                mApartmentAdapter.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
                //Log.e("nhà trọ",response.toString())
            })

        //val client = ApartmentsServiceClient.create()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        //super.onCreateOptionsMenu(menu, inflater)


        mSearch = menu.findItem(R.id.action_search)
        mSearchView = mSearch.actionView as SearchView

        // listening to search query text change
        // listening to search query text change
        mSearchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                mApartmentAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                mApartmentAdapter.filter.filter(query)
                return false
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_filter -> {
                val fm = fragmentManager
                val dialogFragment = FilterDialogFragment()
                if (fm != null) {
                    dialogFragment.setTargetFragment(this, RC_HOME)
                    dialogFragment.show(fm, "filter")
                }
                true
            }
            R.id.action_search -> {
                Log.i("menu click", "search icon click")
                true
            }
            R.id.action_refresh -> {
                swipeRefreshLayout.isRefreshing = true
                loadApartment(response.getToken())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_HOME -> {
                val district = data?.getStringExtra("district")
                val area = data?.getIntExtra("area", -1)
                val price = data?.getFloatExtra("price", -1F)

                mApartmentAdapter.updateApartments(district, area, price)
            }
            RC_CANCEL -> {
                //loadApartment()
                //clear filter
                mApartmentAdapter.filter.filter(mSearchView.query)
            }
        }
    }

    override fun onDataReceived(apartmentId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}



