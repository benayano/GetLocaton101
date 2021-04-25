package com.example.getlocation101

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location

import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 15
    }

    private lateinit var locationManager: LocationManager

    private val longitude: TextView by lazy { findViewById(R.id.longitude) }
    private val latitude: TextView by lazy { findViewById(R.id.latitude) }
    private val button: Button by lazy { findViewById(R.id.button) }

    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestLocationPermission()
        button.setOnClickListener {
            if (permissionLocationGranted()) {
                Toast.makeText(this, "יש הרשאת גישה למיקום 11111111", Toast.LENGTH_LONG).show()
                getLocation()
            } else {
                Toast.makeText(this, " אין הרשאת גישה למיקום 22222222", Toast.LENGTH_LONG).show()
                requestLocationPermission()
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                onLocationChanged(
                    it
                )
            }


    }

    private fun requestLocationPermission() {
        if (!permissionLocationGranted()) {
            ActivityCompat.requestPermissions(
                this,
                permissions,
                PERMISSION_REQUEST_CODE
            )
        }

    }

    private fun permissionLocationGranted() = ContextCompat.checkSelfPermission(
        applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            resultLocationPermission(grantResults)
        }
    }

    private fun onLocationChanged(location: Location) {
         longitude.text = "Latitude: ${location.latitude}"
          latitude.text = "Longitude:  ${ location.longitude }"
        Toast.makeText(
            this,
            "Latitude: ${location.latitude}  \n , Longitude:  ${location.longitude}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun resultLocationPermission(grantResults: IntArray) {
        if (grantResults.isNotEmpty() || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

}














