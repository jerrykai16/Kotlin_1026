package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),LocationListener {
    private lateinit var binding:ActivityMainBinding
    private var hasGPS:Boolean = false
    private var hasNetwork:Boolean = false
    private lateinit var locationManager: LocationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //檢視GPS和Network
        check()
        //登記location / GPS
        if (hasGPS || hasNetwork){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                // return
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1.0f,this)

        }else{
            binding.tv2.text = "設備未提供定位服務"
        }
    }

    override fun onResume() {
        super.onResume()
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
    }
    override fun onPause() {
        super.onPause()
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(p0: Location) {
        binding.tv2.text = p0.longitude.toString()
        binding.textView3.text = p0.latitude.toString()
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