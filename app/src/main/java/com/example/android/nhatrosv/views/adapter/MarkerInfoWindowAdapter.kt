package com.example.android.nhatrosv.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.android.nhatrosv.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

data class MarkerInfoWindowAdapter(val mContext: Context) :
    GoogleMap.InfoWindowAdapter {
    private val mWindow: View =
        LayoutInflater.from(mContext).inflate(R.layout.custom_marker_info_window, null)

    private fun renderWindowText(marker: Marker, view: View){
        val title = marker.title
        val tvTitle: TextView = view.findViewById(R.id.textView_title)

        tvTitle.text = title

        val snippet = marker.snippet
        val tvSnippet: TextView = view.findViewById(R.id.textView_snippet)
        tvSnippet.text = snippet
    }

    override fun getInfoContents(p0: Marker?): View {
        if (p0 != null) {
            renderWindowText(p0,mWindow)
        }
        return mWindow
    }

    override fun getInfoWindow(p0: Marker?): View {
        if (p0 != null) {
            renderWindowText(p0,mWindow)
        }
        return mWindow
    }
}