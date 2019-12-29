package com.example.android.nhatrosv.views.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Comment
import com.example.android.nhatrosv.models.Token
import com.example.android.nhatrosv.utils.*
import com.example.android.nhatrosv.viewModels.MainActivityViewModel
import com.example.android.nhatrosv.views.adapter.CommentAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.squareup.picasso.Picasso

class ApartmentDetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var imageView: ImageView
    lateinit var editTextComment: EditText
    lateinit var textViewName: TextView
    lateinit var textViewPrice: TextView
    lateinit var textViewArea: TextView
    lateinit var textViewAddress: TextView
    lateinit var textViewDate: TextView
    lateinit var textViewDetails: TextView
    private var apartmentId: Int = 0
    private var mApartment: Apartment? = null
    var mComments: List<Comment> = ArrayList()
    lateinit var mCommentAdapter: CommentAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mInterstitialAd: InterstitialAd

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var token: Token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apartment_detail)
        sharedPreferences = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        token = Token(sharedPreferences.get("token", "null"))

        toolbar = findViewById(R.id.toolbar2)
        imageView = findViewById(R.id.imageView)
        editTextComment = findViewById(R.id.editText_comment)

        textViewName = findViewById(R.id.textView_name)
        textViewPrice = findViewById(R.id.textView_price)
        textViewArea = findViewById(R.id.textView_area)
        textViewAddress = findViewById(R.id.textView_Address)
        textViewDate = findViewById(R.id.textView_date)
        textViewDetails = findViewById(R.id.textView_detail)
        recyclerView = findViewById(R.id.recyclerViewComments)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = ""
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        imageView.setOnClickListener {
            //launchActivity<ListImageActivity> { }
        }

        editTextComment.onRightDrawableClicked {
            val userId = 1
            val content = it.text.toString()
            sendComment(token.getToken(), apartmentId, userId, content)
            TOAST("sent", Toast.LENGTH_LONG)
            it.text.clear()
        }
        viewModel = getViewModel<MainActivityViewModel>()
        mCommentAdapter = CommentAdapter(ArrayList())
        recyclerView.adapter = mCommentAdapter
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        loadData()

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        //create ads
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                //super.onAdClosed()
                val sdt = mApartment!!.phoneNumber
                if (sdt != null) {
                    TOAST("call: $sdt", Toast.LENGTH_LONG)
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$sdt")
                    startActivity(intent)
                }
            }
        }
    }

    private fun loadData() {
//        swipeRefreshLayout.isRefreshing = true
//        apartment = intent.getSerializableExtra("apartment") as? Apartment
//        if (apartment != null) {
//            textViewName.text = apartment?.name
//            textViewPrice.text = apartment?.price.toString()
//            textViewArea.text = "${apartment?.area.toString()} m\u00B2"
//            textViewAddress.text = "${apartment?.street} ${apartment?.district}"
//            textViewDate.text = apartment?.date
//            Picasso.get().load(Uri.parse(apartment?.imageURl.toString())).into(imageView)
//            textViewDetails.text = apartment?.details
//            //load comment
//            val id = apartment!!.id
//            if (id != null)
//                loadComments(id)
//
//        }
//        mCommentAdapter.notifyDataSetChanged()

        swipeRefreshLayout.isRefreshing = true
        apartmentId = intent.getIntExtra("apartmentId", 0)
        if (apartmentId != 0) {
            getApartment(token.getToken(), apartmentId)
        }

        //swipeRefreshLayout.isRefreshing = false
    }

    private fun getApartment(token: String, id: Int) {
        viewModel.getApartment(token, id).observe(this,
            Observer { response ->
                mApartment = response
                textViewName.text = response?.name
                textViewPrice.text = response?.price.toString()
                textViewArea.text = "${response?.area.toString()} m\u00B2"
                textViewAddress.text = "${response?.address} ${response?.district}"
                textViewDate.text = response?.date
                Picasso.get().load(Uri.parse(response?.imageURl.toString())).into(imageView)
                //textViewDetails.text = response?.details

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    textViewDetails.text =
                        Html.fromHtml(response?.details, Html.FROM_HTML_MODE_LEGACY)
                else
                    textViewDetails.text = Html.fromHtml(response?.details)

                swipeRefreshLayout.isRefreshing = false
            })
        //load comment
        loadComments(token, id)
    }

    private fun sendComment(token: String, apartmentId: Int, userId: Int, content: String) {
        hideSoftKeyboard()
        if (apartmentId != 0) {
            viewModel.sendComment(token, apartmentId, userId, content).observe(this,
                Observer { response ->
                    //                    if (response == "ok") {
//                        TOAST("sent successfully", Toast.LENGTH_SHORT)
//
//                        loadData()
//                    } else {
//                        TOAST("sent failed", Toast.LENGTH_SHORT)
//                    }
                    if (response != null) {
                        loadData()

                    }
                })
        }
    }

    private fun loadComments(token: String, id: Int) {
//        viewModel.getApartments().observe(this,
//            Observer { response ->
//                mApartments = response
//                mApartmentAdapter.updateApartments(mApartments)
//                mApartmentAdapter.notifyDataSetChanged()
//                swipeRefreshLayout.isRefreshing = false
//                //Log.e("nhà trọ",response.toString())
//            })
        viewModel.getComments(token, id).observe(this,
            Observer { response ->
                mComments = response
                mCommentAdapter.updateComment(mComments)
                Log.e("bình luận", response.toString())
                mCommentAdapter.notifyDataSetChanged()


            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_apartment_detail, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_call -> {
                loadAdsCall()
//                val sdt = mApartment!!.phoneNumber
//                if (sdt != null) {
//                    TOAST("call: $sdt", Toast.LENGTH_LONG)
//                    val intent = Intent(Intent.ACTION_DIAL)
//                    intent.data = Uri.parse("tel:$sdt")
//                    startActivity(intent)
//                }
            }
            R.id.action_direction -> {
                //TOAST("direction", Toast.LENGTH_LONG)
                launchActivity<MainActivity> {
                    putExtra("mode", 1)
                    putExtra("id", mApartment!!.id)
                }
                //finish()
            }
            R.id.action_share -> {
                TOAST("share", Toast.LENGTH_LONG)
            }
            R.id.action_refresh -> {
                loadData()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadAdsCall() {
        mInterstitialAd.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
