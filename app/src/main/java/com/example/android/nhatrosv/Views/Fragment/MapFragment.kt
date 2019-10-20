package com.example.android.nhatrosv.Views.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.android.nhatrosv.R
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.view.*


class MapFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    lateinit var mapView: MapView

//    companion object {
//        fun newInstance(): MapFragment {
//            return MapFragment()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        mapView = rootView.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        mapView.getMapAsync(OnMapReadyCallback {
            mMap = it
            val hcmute = LatLng(-34.0, 151.0)
            mMap.addMarker(MarkerOptions().title("hcmute nè").position(hcmute))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmute))
            mMap.isMyLocationEnabled =true
            mMap.uiSettings.isMyLocationButtonEnabled = true
        })
        return rootView
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
