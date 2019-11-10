package com.example.android.nhatrosv.Views.Fragment

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.Views.Activity.TOAST
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.view.*


class MapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {


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
            //val user_location = LocationServices.getFusedLocationProviderClient()
            mMap.addMarker(MarkerOptions().title("hcmute nè").position(hcmute))
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmute))
            mMap.isMyLocationEnabled = true

            mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isMapToolbarEnabled = false

            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)

        })
        return rootView
    }

    override fun onMyLocationClick(p0: Location) {
        TOAST("Current location:\n" + p0, Toast.LENGTH_LONG)
    }

    override fun onMyLocationButtonClick(): Boolean {
        TOAST("MyLocation button clicked", Toast.LENGTH_SHORT)
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
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
