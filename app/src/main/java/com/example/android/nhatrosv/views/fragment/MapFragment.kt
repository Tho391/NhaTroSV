package com.example.android.nhatrosv.views.fragment

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.utils.TOAST
import com.example.android.nhatrosv.utils.getViewModel
import com.example.android.nhatrosv.viewModels.MainActivityViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.example.android.nhatrosv.utils.*


class MapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    private val listStep: ArrayList<LatLng> = ArrayList()
    private val polyline: PolylineOptions = PolylineOptions()

    lateinit var mapView: MapView
    private lateinit var searchView: SearchView
    private var myLat: Double = 0.0
    private var myLng: Double = 0.0

    var mApartments: List<Apartment> = ArrayList()

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private val KEY_CAMERA_POSITION = "camera_position"
    private val KEY_LOCATION = "location"
//    companion object {
//        fun newInstance(): MapFragment {
//            return MapFragment()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val viewModel = getViewModel<MainActivityViewModel>()
//        viewModel.getApartments().observe(this,
//            Observer {response ->
//                mApartments = response
//                //Log.e("nhà trọ",response.toString())
//            })
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity!!.baseContext)
    }

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
        mapView.getMapAsync(this)

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

        //addMarker()

        return rootView
    }


    private fun addMarker() {
        val viewModel = getViewModel<MainActivityViewModel>()
        viewModel.getApartments().observe(this,
            Observer { response ->

                mApartments = response
                //Log.e("nhà trọ",response.toString())

                val markerOptions = MarkerOptions()
                val bitmapDescriptor =
                    BitmapDescriptorFactory.fromResource(R.mipmap.house_marker)

                for (i in mApartments) {
                    val lat = i.localX
                    val lng = i.localY
                    val latLng = LatLng(lat!!, lng!!)
                    markerOptions.position(latLng)
                    markerOptions.title(i.name)
                    markerOptions.icon(bitmapDescriptor)
                    markerOptions.snippet(i.getInfo())
                    val marker = mMap.addMarker(markerOptions)
                    marker.tag = i.localX.toString() + ";" + i.localY.toString()

                }
            })
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

    override fun onMapReady(p0: GoogleMap?) {


        if (p0 != null) {
            mMap = p0
            mMap.setOnMarkerClickListener(this)
            val hcmute = LatLng(-34.0, 151.0)

            //val user_location = LocationServices.getFusedLocationProviderClient()
            var marker = mMap.addMarker(
                MarkerOptions()
                    .title("hcmute nè")
                    .position(hcmute)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.house_marker))
            )

            //mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmute))
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(myLat, myLng)))

            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMapToolbarEnabled = true

            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)

            addMarker()

            mFusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    myLat = it.latitude
                    myLng = it.longitude
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(myLat, myLng)))
                    mMap.animateCamera(
                        CameraUpdateFactory
                            .newLatLngZoom(LatLng(myLat, myLng), 15F)

                    )
                    TOAST("my marker " + myLat + "|" + myLng, Toast.LENGTH_SHORT)
                }
            }
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        mMap.uiSettings.isMapToolbarEnabled =true

        p0?.showInfoWindow()


        val tag: String = p0?.tag as String
        val coordinate = tag.split(';')

        TOAST(coordinate.toString(), Toast.LENGTH_LONG)

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    coordinate[0].toDouble(),
                    coordinate[1].toDouble()
                ), 17F
            )
        )



        return true
    }

    fun makeURL(
        sourcelat: String,
        sourcelng: String,
        destlat: String,
        destlng: String
    ): String? {
        val urlString = StringBuilder()
        urlString.append("https://maps.googleapis.com/maps/api/directions/json")
        urlString.append("?origin=") // from
        urlString.append(sourcelat)
        urlString.append(",")
        urlString.append(sourcelng)
        urlString.append("&destination=") // to
        urlString.append(destlat)
        urlString.append(",")
        urlString.append(destlng)
        urlString.append("&key=" + resources.getString(R.string.NhaTroSV_Google_Map_Api_Key))
        return urlString.toString()
    }

}
