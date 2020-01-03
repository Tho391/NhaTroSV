package com.example.android.nhatrosv.views.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Apartment
import com.example.android.nhatrosv.models.Response
import com.example.android.nhatrosv.utils.CharacterTransform.convertString
import com.example.android.nhatrosv.utils.TOAST
import com.example.android.nhatrosv.utils.get
import com.example.android.nhatrosv.utils.getViewModel
import com.example.android.nhatrosv.utils.launchActivity
import com.example.android.nhatrosv.viewModels.MainActivityViewModel
import com.example.android.nhatrosv.views.activity.ApartmentDetailActivity
import com.example.android.nhatrosv.views.activity.MainActivity
import com.example.android.nhatrosv.views.activity.OnDataReceivedListener
import com.example.android.nhatrosv.views.adapter.MarkerInfoWindowAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*


class MapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    OnDataReceivedListener, GoogleMap.OnInfoWindowClickListener, SearchView.OnQueryTextListener {

    private lateinit var mInterstitialAd: InterstitialAd
    private val RC_MAP: Int = 1000
    private val RC_CANCEL: Int = 1001
    private lateinit var mMap: GoogleMap

    private val listStep: ArrayList<LatLng> = ArrayList()
    private val polyline: PolylineOptions = PolylineOptions()

    lateinit var mapView: MapView
    private lateinit var searchView: SearchView
    private var myLat: Double = 0.0
    private var myLng: Double = 0.0
    lateinit var imageViewRoute: ImageView
    lateinit var imageViewCall: ImageView
    lateinit var imageViewLocation: ImageView
    lateinit var imageViewFilter: ImageView

    private var mApartments: List<Apartment> = ArrayList()
    var markerList: ArrayList<Marker> = ArrayList()
    var passingApartmentId: Int? = null
    lateinit var mainActivity: MainActivity

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    lateinit var sharedPreferences: SharedPreferences
    lateinit var response: Response

    lateinit var markerInfoWindowAdapter: MarkerInfoWindowAdapter

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
        sharedPreferences = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        response= Response(sharedPreferences.get("token","null"),null)

        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(activity!!.baseContext)

        //passing data latlng to map
        mainActivity = activity as MainActivity
        mainActivity.mDataReceivedListener = this

        //create ads
        mInterstitialAd = InterstitialAd(requireContext())
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
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

        val googleText = (mapView.findViewById<View>(Integer.parseInt("1")).parent as View)
            .findViewById<View>(Integer.parseInt("4"))
        googleText.visibility = View.INVISIBLE
        val rlp3 = googleText.layoutParams as RelativeLayout.LayoutParams
        // position on right bottom
        rlp3.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp3.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)


        searchView = rootView.findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(this)
        imageViewFilter = rootView.findViewById(R.id.imageView_filter)
        imageViewFilter.setOnClickListener {
            val fm = fragmentManager
            val dialogFragment = FilterDialogFragment()
            if (fm != null) {
                dialogFragment.setTargetFragment(this, RC_MAP)
                dialogFragment.show(fm, "filter")
            }
        }
        imageViewRoute = rootView.findViewById(R.id.imageView_direction)
        imageViewCall = rootView.findViewById(R.id.imageView_call)

        imageViewLocation = rootView.findViewById(R.id.imageView_location)
        imageViewLocation.setOnClickListener {
            mMap.animateCamera(
                CameraUpdateFactory
                    .newLatLngZoom(LatLng(myLat, myLng), 15F)
            )
        }


        markerInfoWindowAdapter = MarkerInfoWindowAdapter(this.requireContext())

        return rootView
    }


    private fun addMarker() {
        val viewModel = getViewModel<MainActivityViewModel>()
        viewModel.getApartments(response.getToken()).observe(this,
            Observer { response ->

                mApartments = response
                //Log.e("nhà trọ",response.toString())

//                val markerOptions = MarkerOptions()
//                val bitmapDescriptor =
//                    BitmapDescriptorFactory.fromResource(R.mipmap.house_marker)
//                for (i in mApartments) {
//                    val lat = i.localX
//                    val lng = i.localY
//                    val latLng = LatLng(lat!!, lng!!)
//                    markerOptions.position(latLng)
//                    markerOptions.title(i.name)
//                    markerOptions.icon(bitmapDescriptor)
//                    markerOptions.snippet(i.getInfo())
//                    val marker = mMap.addMarker(markerOptions)
//                    marker.tag = "${i.localX};${i.localY};${i.phoneNumber}"
//                    if (markerList.all { it != marker })
//                        markerList.add(marker)
//                }
                markerList = ArrayList(createMarkers(mApartments))
            })
    }

    private fun createMarkers(mApartments: List<Apartment>): List<Marker> {
        val markerList: ArrayList<Marker> = ArrayList()
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
            //add marker to map view
            val marker = mMap.addMarker(markerOptions)
            marker.tag = "${i.localX};${i.localY};${i.phoneNumber};${i.id}"

            if (markerList.all { it != marker })
                markerList.add(marker)
        }
        return markerList
    }

    override fun onMyLocationClick(p0: Location) {
        TOAST("Current location:\n$p0", Toast.LENGTH_LONG)
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
            mMap.setOnInfoWindowClickListener(this)
            mMap.setOnMapClickListener {
                imageViewRoute.visibility = View.INVISIBLE
                imageViewCall.visibility = View.INVISIBLE
            }
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
            mMap.uiSettings.isMapToolbarEnabled = false

            mMap.setOnMyLocationClickListener(this)

            mMap.setInfoWindowAdapter(markerInfoWindowAdapter)

            addMarker()

            if (passingApartmentId == null)
                mFusedLocationClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        myLat = it.latitude
                        myLng = it.longitude
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(myLat, myLng)))
                        mMap.animateCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(LatLng(myLat, myLng), 15F)
                        )
                        //TOAST("my marker $myLat|$myLng", Toast.LENGTH_SHORT)
                    }
                }
            else
                animateCamera(passingApartmentId!!)
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        imageViewRoute.visibility = View.VISIBLE
        imageViewCall.visibility = View.VISIBLE

        //mMap.uiSettings.isMapToolbarEnabled = true

        p0?.showInfoWindow()


        val tag: String = p0?.tag as String
        val data = tag.split(';')
        val lat = data[0].toDouble()
        val lng = data[1].toDouble()
        val sdt = data[2]
        //TOAST(data.toString(), Toast.LENGTH_LONG)

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(lat, lng), 17F
            )
        )

        imageViewRoute.setOnClickListener {
            val uri =
                Uri.parse("http://maps.google.com/maps?saddr=$myLat,$myLng&daddr=$lat,$lng")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        imageViewCall.setOnClickListener {
//            val intent = Intent(Intent.ACTION_DIAL)
//            intent.data = Uri.parse("tel:$sdt")
//            startActivity(intent)

            mInterstitialAd.adListener = object : AdListener(){
                override fun onAdClosed() {
                    //super.onAdClosed()
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:$sdt")
                    startActivity(intent)
                }
            }
            mInterstitialAd.show()
        }


        return true
    }

    override fun onInfoWindowClick(p0: Marker?) {
        val tag = p0?.tag as String
        val data = tag.split(";")
        val id = data[3].toInt()
        requireContext().launchActivity<ApartmentDetailActivity> {
            putExtra("apartmentId", id)
        }
    }

    override fun onDataReceived(apartmentId: Int) {
        //TOAST("truyền id $apartmentId", Toast.LENGTH_SHORT)
        animateCamera(apartmentId)
        passingApartmentId = apartmentId
    }

    private fun animateCamera(apartmentId: Int) {
        val apartment = mApartments.firstOrNull { it.id == apartmentId }
        if (apartment != null) {
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(apartment.localX!!.toDouble(), apartment.localY!!.toDouble()), 17F
                )
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_MAP -> {
                val district = data?.getStringExtra("district")
                val area = data?.getIntExtra("area", -1)
                val price = data?.getFloatExtra("price", -1F)


                //xóa marker
                for (m in markerList)
                    m.remove()
                //add marker theo điều kiện
                markerList =
                    ArrayList(createMarkers(mApartments, district, area, price))
                //mApartmentAdapter.updateApartments(district,area,price)
            }
            RC_CANCEL -> {
                //loadApartment()
                //clear filter
                //xóa marker
                for (m in markerList)
                    m.remove()
                addMarker()
                //mApartmentAdapter.filter.filter(mSearchView.query)
            }
        }
    }

    //create marker from filter apartments and add to map view
    private fun createMarkers(
        mApartments: List<Apartment>,
        district: String?,
        area: Int?,
        price: Float?
    ): List<Marker> {
        var temp: List<Apartment> = mApartments
        if (district != null && district != "Tất cả")
            temp = temp.filter { it.district == district }
        if (area != null && area > 0)
            temp = temp.filter { it.area!! < area }
        if (price != null && price > 0)
            temp = temp.filter { it.price!! < price }
        return createMarkers(temp)
    }

    private fun createMarkers(mApartments: List<Apartment>, newText: String?): List<Marker> {
        var temp = mApartments
        if (newText != null)
            temp =
                temp.filter {
                    convertString(it.address + it.district)
                        .contains(convertString(newText))
                }
        return createMarkers(temp)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return if (newText != null) {
            //xóa marker
            for (m in markerList)
                m.remove()
            //tạo marker từ query và filter
            markerList = ArrayList(createMarkers(mApartments, newText))
            false
        } else {
            for (m in markerList)
                m.remove()
            addMarker()
            true
        }
    }


}
