package com.example.agh.task.presentation.location

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.agh.task.R
import com.example.agh.task.presentation.Utils.toast
import kotlinx.android.synthetic.main.activity_location.*

const val ACTION_NEW_LOCATION= "NEW_LOCATION"

class LocationActivity : AppCompatActivity() {

    private val REQUEST_PERMISSION_LOCATION = 10




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)



     val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
     if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
       "Please Make sure your GPS is Enabled".toast(this)
     }

        val locationService = Intent(applicationContext, LocationService::class.java)
     btnStart.setOnClickListener {
         if (checkPermissionForLocation(this)) {

             startService(locationService)
             btnStart.isEnabled = false
             btnStop.isEnabled = true
         }
     }

     btnStop.setOnClickListener {
     //  Intent(ACTION_STOP_LOCATION_UPDATES).also {sendBroadcast(it)  }
         stopService(locationService)
       "Updates Stopped".toast(this)
         btnStart.isEnabled = true
         btnStop.isEnabled = false
     }


     registerReceiver(newLocationReceiver, IntentFilter(ACTION_NEW_LOCATION))
    }

    override fun onDestroy() {
        unregisterReceiver(newLocationReceiver)

        super.onDestroy()
    }
    private val newLocationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
          intent?.extras?.get("newCoo").run { tvLocation.text = this.toString() }
        }
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(applicationContext, LocationService::class.java)
                startService(intent)
                btnStart.isEnabled = false
                btnStop.isEnabled = true
            } else {
             "Permission Denied".toast(this)
            }
        }
    }

    fun checkPermissionForLocation(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // Show the permission request
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }
}