package com.example.android.nhatrosv.views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.views.TOAST

class ApartmentDetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apartment_detail)

        toolbar = findViewById(R.id.toolbar2)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = "Nhà Trọ Sinh Viên"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_apartment_detail,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_call ->{
                TOAST("call",Toast.LENGTH_LONG)
                return true
            }
            R.id.action_direction->{
                TOAST("direction",Toast.LENGTH_LONG)
                return true
            }
            R.id.action_share->{
                TOAST("share",Toast.LENGTH_LONG)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
