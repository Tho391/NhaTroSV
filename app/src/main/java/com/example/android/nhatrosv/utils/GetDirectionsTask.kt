package com.example.android.nhatrosv.utils

import com.example.android.nhatrosv.models.Directions
import com.example.android.nhatrosv.models.Directions.Leg
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.MalformedURLException
import java.net.URL

class GetDirectionsTask(private val mRequest: String) {
    fun testDirection(): ArrayList<LatLng> {
        val ret = ArrayList<LatLng>()
        try {
            val url: URL = URL(mRequest)
            val reader = InputStreamReader(url.openStream(), "UTF-8")
            val results: Directions = Gson().fromJson(reader, Directions::class.java)
            val routes: Array<Directions.Route> = results.routes
            val leg: Array<Leg> = routes[0].legs
            val steps: Array<Directions.Leg.Step> = leg[0].steps
            for (step in steps) {
                val latlngStart = LatLng(
                    step.start_location.lat,
                    step.start_location.lng
                )
                val latlngEnd = LatLng(
                    step.end_location.lat,
                    step.end_location.lng
                )
                ret.add(latlngStart)
                ret.add(latlngEnd)
            }
            return ret
        } catch (e: MalformedURLException) { // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) { // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) { // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return ret
    }

}
