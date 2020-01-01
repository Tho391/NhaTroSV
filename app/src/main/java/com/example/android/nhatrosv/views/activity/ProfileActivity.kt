package com.example.android.nhatrosv.views.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Response
import com.example.android.nhatrosv.utils.get
import com.example.android.nhatrosv.utils.getViewModel
import com.example.android.nhatrosv.viewModels.LoginActivityViewModel
import java.util.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var editTextLastName: EditText
    lateinit var editTextFirstName: EditText
    lateinit var editTextAddress: EditText
    lateinit var editTextPhone: EditText
    lateinit var textViewDate: TextView
    lateinit var buttonSave: Button
    lateinit var buttonCancel: Button
    lateinit var imageViewDate: ImageView

    private lateinit var dialog: DatePickerDialog

    var response: Response? = null
    lateinit var sharedPreferences: SharedPreferences

    lateinit var viewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        editTextLastName = findViewById(R.id.editText_lastName)
        editTextFirstName = findViewById(R.id.editText_firstName)
        editTextAddress = findViewById(R.id.editText_address)
        editTextPhone = findViewById(R.id.editText_phone)
        textViewDate = findViewById(R.id.textView_date)
        buttonSave = findViewById(R.id.button_save)
        buttonCancel = findViewById(R.id.button_cancel)
        imageViewDate = findViewById(R.id.imageView_date)

        buttonSave.setOnClickListener(this)
        buttonCancel.setOnClickListener(this)
        imageViewDate.setOnClickListener(this)

        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        dialog = DatePickerDialog(
            this, R.style.Theme_MaterialComponents_Light_Dialog,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                textViewDate.text = "$day-$month-$year"
            }, mYear, mMonth, mDay
        )

        sharedPreferences =
            getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

        response = Response(sharedPreferences.get("token", "null"), null)
        viewModel = getViewModel()
    }

    override fun onStart() {
        super.onStart()
        if (response?.accessToken != null)
        viewModel.getInfo(response!!.getToken())
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.button_save ->{
                //todo gui request sever
            }
            R.id.button_cancel ->{
                finish()
            }
            R.id.imageView_date ->{
                dialog.show()
            }
        }
    }
}
