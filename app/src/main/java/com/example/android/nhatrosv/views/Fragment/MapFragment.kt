package com.example.android.nhatrosv.views.Fragment

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.views.TOAST
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private lateinit var mMap: GoogleMap
    lateinit var mapView: MapView
    private lateinit var searchView: SearchView

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
            //mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isMapToolbarEnabled = true
            //mMap.setPadding(0,100,0,0)

            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
        })

        // Get the button view
        val locationButton =
            (mapView.findViewById<View>(Integer.parseInt("1")).parent as View)
                .findViewById<View>(Integer.parseInt("2"))
        // and next place it, for exemple, on bottom right (as Google Maps app)
        val rlp = locationButton.layoutParams as RelativeLayout.LayoutParams
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        rlp.bottomMargin = 33
        val mapToolbar = (mapView.findViewById<View>(Integer.parseInt("1")).parent as View)
            .findViewById<View>(Integer.parseInt("4"))
        val rlp2 = mapToolbar.layoutParams as RelativeLayout.LayoutParams
        // position on right bottom
        rlp2.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp2.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
        rlp2.rightMargin = 150
        rlp2.bottomMargin = 30

        val googleText = (mapView.findViewById<View>(Integer.parseInt("1")).parent as View)
            .findViewById<View>(Integer.parseInt("4"))
        val rlp3 = googleText.layoutParams as RelativeLayout.LayoutParams
        // position on right bottom
        rlp3.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp3.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)


        searchView = rootView.findViewById(R.id.searchView)


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
