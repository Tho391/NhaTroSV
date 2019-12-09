package com.example.android.nhatrosv.models

class Directions(val routes: Array<Route>, val status: String) {

    class Route(val legs: Array<Leg>)

    class Leg(
        val end_address: String,
        val end_location: End_location,
        val start_address: String,
        val start_location: Start_location,
        val steps: Array<Step>
    ) {

        class End_location(val lat: Double, val lng: Double)

        class Start_location(val lat: Double, val lng: Double)

        class Step(
            val end_location: End_location,
            val html_instructions: String,
            val start_location: Start_location,
            val travel_mode: String,
            val maneuver: String
        )

    }

}
