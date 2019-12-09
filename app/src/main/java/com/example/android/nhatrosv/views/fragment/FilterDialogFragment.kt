package com.example.android.nhatrosv.views.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.android.nhatrosv.R
import com.google.android.material.button.MaterialButton


class FilterDialogFragment : DialogFragment(), SeekBar.OnSeekBarChangeListener,
    View.OnClickListener {

    private val areaState: String = "areaState"
    private val priceState: String = "priceState"
    private val districtState = "districtState"


    lateinit var btnCancel: MaterialButton
    lateinit var btnApply: MaterialButton
    lateinit var seekBarPrice: SeekBar
    lateinit var seekBarArea: SeekBar
    lateinit var spinnerDistrict: Spinner
    lateinit var textViewPrice: TextView
    lateinit var textViewArea: TextView

    lateinit var checkBoxDistrict: CheckBox
    lateinit var checkBoxArea: CheckBox
    lateinit var checkBoxPrice: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_filter_dialog, container, false)

        btnCancel = rootView.findViewById(R.id.btn_cancel)
        btnApply = rootView.findViewById(R.id.btn_apply)
        seekBarPrice = rootView.findViewById(R.id.seekBar_price)
        seekBarArea = rootView.findViewById(R.id.seekBar_area)
        spinnerDistrict = rootView.findViewById(R.id.spinner_district)
        textViewPrice = rootView.findViewById(R.id.textView_price)
        textViewArea = rootView.findViewById(R.id.textView_area)
        checkBoxDistrict = rootView.findViewById(R.id.checkBox_district)
        checkBoxArea = rootView.findViewById(R.id.checkBox_area)
        checkBoxPrice = rootView.findViewById(R.id.checkBox_price)

        seekBarPrice.setOnSeekBarChangeListener(this)

        seekBarArea.setOnSeekBarChangeListener(this)

        btnCancel.setOnClickListener(this)
        btnApply.setOnClickListener(this)

        checkBoxDistrict.setOnCheckedChangeListener { _, b ->
            spinnerDistrict.isClickable = b
        }
        checkBoxArea.setOnCheckedChangeListener { _, b ->
            seekBarArea.isClickable = b
        }
        checkBoxPrice.setOnCheckedChangeListener { _, b ->
            seekBarPrice.isClickable = b
        }

        return rootView
    }



    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        when (p0?.id) {
            R.id.seekBar_price -> {
                checkBoxPrice.isChecked =true
                val i: Float = (seekBarPrice.progress).toFloat() / 10
                textViewPrice.text = i.toString()
            }
            R.id.seekBar_area -> {
                checkBoxArea.isChecked = true
                val i: Int = seekBarArea.progress
                textViewArea.text = i.toString()
            }
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_apply -> {
                sendResult(1000)
                dismiss()
            }
            R.id.btn_cancel -> {
                sendResult(1001)
                dismiss()
            }
        }
    }

    private fun sendResult(REQUEST_CODE: Int) {
        val intent = Intent()
        val district = spinnerDistrict.selectedItem.toString()
        val area = textViewArea.text.toString().toInt()
        val price = textViewPrice.text.toString().toFloat() * 1000000
        if (checkBoxDistrict.isChecked)
            intent.putExtra("district", district)
        if (checkBoxArea.isChecked)
            intent.putExtra("area", area)
        if (checkBoxPrice.isChecked)
            intent.putExtra("price", price)
        targetFragment!!.onActivityResult(
            targetRequestCode, REQUEST_CODE, intent
        )
    }

}