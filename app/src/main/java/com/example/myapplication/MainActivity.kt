package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),LocationListener {
    private lateinit var binding:ActivityMainBinding
    private var hasGPS:Boolean = false
    private var hasNetwork:Boolean = false
    private lateinit var locationManager: LocationManager
    private lateinit var location: Location
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //檢視GPS和Network
        check()

    }

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }
    //檢視GPS和Network
    private fun check(){
        hasGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        var check:String = ""
        if(hasGPS)
            check += "設備有GPS\n"
        if(hasNetwork)
            check += "設備有網絡\n"
        binding.tv1.text = check
    }
}