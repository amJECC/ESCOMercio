package com.example.escomercio

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_verificar_gps.*

class VerificarGPS : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val PERMISSION_ID = 42

    private lateinit var user : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificar_gps)

        user = intent.getStringExtra("Usuario").toString()   //Obtenemos el usuario de la vista anterior

        //Guardar Sesion

        val pref : SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file),
            MODE_PRIVATE
        ).edit()
        pref?.putString("user",user)
        pref?.apply()

        TV_Saludo.setText("HOLA $user . . . Danos un momento, estamos verificando tu ubicacion")

        if(Permiso_Concedido()){

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            leerUbicacion()

        }else{

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)

        }

    }

    private fun leerUbicacion() {
        if(checkPermission()){
            if(isLocationEnabled()){
                if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    fusedLocationClient.lastLocation.addOnCompleteListener(this){ task ->

                        var location : Location? = task.result

                        if(location == null){
                            requestNewLocationData()
                        }else{
                            var latitud = location.latitude
                            var longitud = location.longitude

                            tv_latitud.setText("Latitud = " + latitud.toString())
                            tv_longitud.setText("Longitud = " + longitud.toString())

                            if(latitud == 19.58 && longitud == -99.16){


                                val intentCodigo = Intent(this,PrincipalActivity::class.java).apply {

                                    putExtra("Usuario", user)

                                }
                                startActivity(intentCodigo)

                                finish()

                            }else{
                                val intentCodigo = Intent(this,PrincipalActivity::class.java).apply {

                                    putExtra("Usuario", user)

                                }
                                startActivity(intentCodigo)

                                finish()
                                tv_error.setText("Ubicacion NO Valida")
                            }
                        }

                    }

                }
            }else{
                Toast.makeText(this, "Activar Ubicacion", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
                this.finish()
            }
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_ID)
        }
    }

    private fun isLocationEnabled(): Boolean {

        var locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermission() : Boolean{

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            return true
        }

        return false

    }

    private fun requestNewLocationData() {
        val mLocationRequest = com.google.android.gms.location.LocationRequest()
        mLocationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.requestLocationUpdates(mLocationRequest,mlocationCallBack, Looper.myLooper())

    }

    private val mlocationCallBack = object : LocationCallback(){

        override fun onLocationResult(p0: LocationResult) {
            var mLastLocation: Location? = p0.lastLocation

            var latitud = mLastLocation?.latitude
            var longitud = mLastLocation?.longitude

            tv_latitud.setText("cb Latitud = " + latitud.toString())
            tv_longitud.setText("cb Longitud = " + longitud.toString())
        }

    }

    private fun Permiso_Concedido() = REQUIRED_PERMISSION_GPS.all {
        ContextCompat.checkSelfPermission(baseContext,it) == PackageManager.PERMISSION_GRANTED
    }


    companion object {

        private val REQUIRED_PERMISSION_GPS = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION)

    }
}