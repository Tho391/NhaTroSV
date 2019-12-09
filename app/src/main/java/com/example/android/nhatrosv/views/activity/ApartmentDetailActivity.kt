package com.example.android.nhatrosv.views.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Comment
import com.example.android.nhatrosv.utils.TOAST
import com.example.android.nhatrosv.utils.getViewModel
import com.example.android.nhatrosv.utils.launchActivity
import com.example.android.nhatrosv.utils.onRightDrawableClicked
import com.example.android.nhatrosv.viewModels.MainActivityViewModel
import com.example.android.nhatrosv.views.adapter.CommentAdapter
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
    var apartment: Apartment? = null
    var mComments: List<Comment> = ArrayList()
    lateinit var mCommentAdapter: CommentAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apartment_detail)

        toolbar = findViewById(R.id.toolbar2)
        imageView = findViewById(R.id.imageView)
        editTextComment = findViewById(R.id.editText_comment)

        textViewName = findViewById(R.id.textView_name)
        textViewPrice = findViewById(R.id.textView_price)
        textViewArea = findViewById(R.id.textView_area)
        textViewAddress = findViewById(R.id.textView_Address)
        textViewDate = findViewById(R.id.textView_date)
        recyclerView = findViewById(R.id.recyclerViewComments)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = ""
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        imageView.setOnClickListener {
            launchActivity<ListImageActivity> { }
        }

        editTextComment.onRightDrawableClicked {
            val userId = 1
            val content = it.text.toString()
            sendComment(apartment, userId, content)
            TOAST("send comment", Toast.LENGTH_LONG)
            it.text.clear()
        }
        viewModel = getViewModel<MainActivityViewModel>()
        mCommentAdapter = CommentAdapter(ArrayList())
        recyclerView.adapter = mCommentAdapter
        //recyclerView.isNestedScrollingEnabled = false
        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        loadData()

        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

    }

    private fun loadData() {
        swipeRefreshLayout.isRefreshing = true
        apartment = intent.getSerializableExtra("apartment") as? Apartment
        if (apartment != null) {
            textViewName.text = apartment!!.name
            textViewPrice.text = apartment!!.price.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textViewArea.text =
                    Html.fromHtml(apartment!!.area.toString() + " m<sup><small>2</small></sup>", 1)
            } else
                textViewArea.text = apartment!!.area.toString() + " m2"
            textViewAddress.text = apartment!!.street + " " + apartment!!.district
            textViewDate.text = apartment!!.date
            Picasso.get().load(Uri.parse(apartment!!.imageURl.toString())).into(imageView)

            //load comment
            val id = apartment!!.id
            if (id != null)
                loadComments(id)

        }
        //swipeRefreshLayout.isRefreshing = false
    }

    private fun sendComment(apartment: Apartment?, userId: Int, content: String) {
        if (apartment != null) {
            viewModel.sendComment(apartment, userId, content).observe(this,
                Observer { response ->
                    if (response == "ok") {
                        TOAST("sent successfully", Toast.LENGTH_SHORT)

                        loadData()
                    } else {
                        TOAST("sent failed", Toast.LENGTH_SHORT)
                    }
                })
        }
    }

    private fun loadComments(id: Int) {
//        viewModel.getApartments().observe(this,
//            Observer { response ->
//                mApartments = response
//                mApartmentAdapter.updateApartments(mApartments)
//                mApartmentAdapter.notifyDataSetChanged()
//                swipeRefreshLayout.isRefreshing = false
//                //Log.e("nhà trọ",response.toString())
//            })
        viewModel.getComments(id).observe(this,
            Observer { response ->
                mComments = response
                mCommentAdapter.updateComment(mComments)
                Log.e("bình luận", response.toString())
                mCommentAdapter.notifyDataSetChanged()

                swipeRefreshLayout.isRefreshing = false
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
                val sdt = apartment!!.phoneNumber
                if (sdt != null) {
                    TOAST("call: $sdt", Toast.LENGTH_LONG)
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$sdt")
                    startActivity(intent)
                }
            }
            R.id.action_direction -> {
                TOAST("direction", Toast.LENGTH_LONG)
//                launchActivity<MainActivity> {
//                    putExtra("mode", 1)
//                }
                finish()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
