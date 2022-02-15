package com.dott.findrestaurants.map.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dott.findrestaurants.R
import com.dott.findrestaurants.details.view.DetailsFragment
import com.dott.findrestaurants.map.viewmodel.RestaurantsMapViewModel
import com.dott.findrestaurants.map.viewmodel.RestosData
import com.dott.findrestaurants.utils.ConstantStrings
import com.dott.findrestaurants.utils.PermissionsHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.koin.android.viewmodel.ext.android.viewModel


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val viewModel by viewModel<RestaurantsMapViewModel>()
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setUpViewModelListeners()
    }

    private fun setUpViewModelListeners() {
        viewModel.restos.observe(this, {
            if (it.isNotEmpty() && it != null) {
                addMarkersToMap(it)
            }
        })

        viewModel.errorMessage.observe(this, {
            if (it != null) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addMarkersToMap(restos: List<RestosData>) {
        for (resto in restos) {
            val location = LatLng(resto.lat, resto.lon)
            val marker = MarkerOptions()
                .position(location)
                .icon(bitmapDescriptorFromVector(this, R.drawable.resto_icon))
                .snippet(resto.name)
            mMap.addMarker(
                marker
            )?.setTag(resto.id)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val myBottomSheet = DetailsFragment()
        val bundle = Bundle()
        bundle.putString("id", marker.tag.toString())
        myBottomSheet.arguments = bundle
        myBottomSheet.show(supportFragmentManager, myBottomSheet.tag)
        return true
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {

        val background = ContextCompat.getDrawable(context, vectorResId)
        background?.let {
            background.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                background.intrinsicWidth,
                background.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            background.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
        return null
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
        setMyLocationOnMap()
        setPanListener()
    }

    private fun setPanListener() {
        mMap.setOnCameraMoveStartedListener {

            //When pan stops get new result
            viewModel.getNearbyRestaurants(
                ConstantStrings.Keys.FOOD_CATEGORY,
                getNorthEastLonLatFormatted().first,
                getNorthEastLonLatFormatted().second,
                getSouthWestLonLatFormatted().first,
                getSouthWestLonLatFormatted().second
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationOnMap() {
        if (PermissionsHelper.isLocationPermissionGranted(this, this)) {
            // Get My Location and show it on map
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->

                    location?.let {
                        val lat = String.format("%.14f", it.latitude).toDouble()
                        val long = String.format("%.14f", it.longitude).toDouble()
                        val myLocation = LatLng(lat, long)
                        mMap.addMarker(MarkerOptions().position(myLocation))
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                myLocation,
                                20f
                            )
                        )
                        viewModel.getNearbyRestaurants(
                            ConstantStrings.Keys.FOOD_CATEGORY,
                            getNorthEastLonLatFormatted().first,
                            getNorthEastLonLatFormatted().second,
                            getSouthWestLonLatFormatted().first,
                            getSouthWestLonLatFormatted().second
                        )
                    }

                }

        }
    }

    private fun getNorthEastLonLatFormatted(): Pair<Double, Double> {
        val bounds: LatLngBounds =
            mMap.projection.visibleRegion.latLngBounds
        val northeast = bounds.northeast
        return Pair(
            String.format("%.14f", northeast.latitude).toDouble(),
            String.format("%.14f", northeast.longitude).toDouble()
        )
    }

    private fun getSouthWestLonLatFormatted(): Pair<Double, Double> {
        val bounds: LatLngBounds =
            mMap.projection.visibleRegion.latLngBounds
        val southwest = bounds.southwest
        return Pair(
            String.format("%.14f", southwest.latitude).toDouble(),
            String.format("%.14f", southwest.longitude).toDouble()
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ConstantStrings.Permissions.PERMISSON_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    setMyLocationOnMap()
                } else {
                    // permission was denied
                }
            }
        }
    }
}