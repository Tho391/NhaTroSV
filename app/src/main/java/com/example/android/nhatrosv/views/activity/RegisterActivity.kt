package com.example.android.nhatrosv.views.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.android.nhatrosv.R

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var buttonCancel: Button
    lateinit var buttonRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonCancel = findViewById(R.id.button_cancel)
        buttonCancel.setOnClickListener(this)
        buttonRegister = findViewById(R.id.button_register)
        buttonRegister.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button_cancel -> {
                finish()
            }
            R.id.button_register -> {
                //gui request lên server
                //dang ki thanh cong -> quay ve login
                //that bai -> bao loi
            }
        }

    }
}
