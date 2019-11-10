package com.example.android.nhatrosv.views.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.nhatrosv.R

class ProfileFragment : Fragment() {

//    companion object{
//        fun newInstance(): ProfileFragment {
//            return ProfileFragment()
//        }
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile,container,false)
        //return super.onCreateView(inflater, container, savedInstanceState)
        return rootView
    }

}
