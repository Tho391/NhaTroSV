package com.example.android.nhatrosv.views.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.utils.TOAST
import com.example.android.nhatrosv.utils.getViewModel
import com.example.android.nhatrosv.viewModels.RegisterActivityViewModel
import java.util.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var buttonCancel: Button
    lateinit var buttonRegister: Button

    lateinit var editTextUserName: EditText
    lateinit var editTextPassword: EditText
    lateinit var editTextLastName: EditText
    lateinit var editTextFirstName: EditText
    lateinit var editTextAddress: EditText
    lateinit var editTextPhone: EditText
    lateinit var imageViewDate: ImageView

    lateinit var textViewDate: TextView

    lateinit var viewModel: RegisterActivityViewModel

    private lateinit var dialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonCancel = findViewById(R.id.button_cancel)
        buttonCancel.setOnClickListener(this)
        buttonRegister = findViewById(R.id.button_save)
        buttonRegister.setOnClickListener(this)

        editTextUserName = findViewById(R.id.editText_username)
        editTextPassword = findViewById(R.id.editText_password)
        editTextLastName = findViewById(R.id.editText_lastName)
        editTextFirstName = findViewById(R.id.editText_firstName)
        editTextAddress = findViewById(R.id.editText_address)
        editTextPhone = findViewById(R.id.editText_phone)
        imageViewDate = findViewById(R.id.imageView_date)
        imageViewDate.setOnClickListener(this)
        textViewDate = findViewById(R.id.textView_date)


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

        viewModel = getViewModel()

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button_cancel -> {
                finish()
            }
            R.id.button_save -> {
                //gui request lên server
                //dang ki thanh cong -> quay ve login
                //that bai -> bao loi
                val date = formatDate(textViewDate.text.toString())
                val photoUrl =
                    "https://p7.hiclipart.com/preview/869/483/630/computer-icons-user-profile-clip-art-icon-profile-size.jpg"
                viewModel.register(
                    editTextUserName.text.toString(),
                    editTextPassword.text.toString(),
                    editTextLastName.text.toString(),
                    editTextFirstName.text.toString(),
                    date,
                    editTextAddress.text.toString(),
                    editTextPhone.text.toString(),
                    photoUrl
                ).observe(this, androidx.lifecycle.Observer { response ->
                    if (true) {
                        TOAST("Register successfully!", Toast.LENGTH_SHORT)
                        finish()
                    } else {
                        TOAST("Error! Try again!", Toast.LENGTH_SHORT)
                    }
                })
            }
            R.id.imageView_date -> {
                //hien thi calendar dialog
                dialog.show()
            }
        }

    }

    private fun formatDate(date: String): String {
        val temp = date.split("-")
        return "${temp[2]}-${temp[1]}-${temp[0]}"
    }
}
