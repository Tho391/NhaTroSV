package com.example.android.nhatrosv.views.activity

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.views.adapter.ImageAdapter
import com.example.android.nhatrosv.utils.decodeBitmap

class ListImageActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var recyclerView: RecyclerView

    lateinit var mImageAdapter: ImageAdapter
    lateinit var mImages: List<Bitmap>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_image)

        toolbar = findViewById(R.id.toolbar3)
        recyclerView = findViewById(R.id.recycler_view)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = "Nhà Trọ Sinh Viên"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        //region sample image
        val image1 = decodeBitmap(R.mipmap.nhatro)!!
        val image2 = decodeBitmap(R.mipmap.nhatro)!!
        val image3 = decodeBitmap(R.mipmap.nhatro)!!
        val image4 = decodeBitmap(R.mipmap.nhatro)!!
        val image5 = decodeBitmap(R.mipmap.nhatro)!!
        val image6 = decodeBitmap(R.mipmap.nhatro)!!
        //endregion


        mImages = listOf(image1, image2, image3, image4, image5, image6)
        mImageAdapter = ImageAdapter(mImages)
        recyclerView.adapter = mImageAdapter

        val linearLayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
