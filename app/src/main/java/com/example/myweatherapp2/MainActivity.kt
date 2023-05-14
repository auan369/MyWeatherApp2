package com.example.myweatherapp2

//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale


const val BASE_URL = "https://api.openweathermap.org/"
//@Suppress("DEPRECATION")




class MainActivity : ComponentActivity(), LocationListener  {
    //private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView
    private val locationPermissionCode = 2

    companion object{
        var lat = "1.3521"
        var lon = "103.8198"
        var picID = "01n"
    }



    //private lateinit var tvLatitude: TextView
    //private lateinit var tvLongitude: TextView


    private lateinit var imageView : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val a = getResources().getIdentifier("ic_launcher_background")


        //fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)


        title = "KotlinApp"
        val button: Button = findViewById(R.id.getLocation)
        button.setOnClickListener {
            getLocation()

        }
        //getCurrentLocation()
        //My Interface code
        getLocation()
        getMyData()
    }


    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }
    override fun onLocationChanged(location: Location) {
        //tvGpsLocation = findViewById(R.id.textView)
        //tvGpsLocation.text = "Latitude: " + location.latitude + " , Longitude: " + location.longitude
        lat = ""+ location.latitude
        lon = ""+ location.longitude

        getMyData()

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }





    /*
    private fun getCurrentLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                //final lat and lon
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
                    requestPermission()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task->
                    val location: Location? = task.result
                    if(location == null){
                        Toast.makeText(this,"Null received", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this,"Get Success", Toast.LENGTH_SHORT).show()
                        tvLatitude.text = "" + location.latitude
                        tvLongitude.text = "" + location.longitude

                    }
                }
            }
            else{
                //setting open here
                Toast.makeText(this,"Turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        }
        else{
            //request permission here
            requestPermission()
        }

    }

    private fun isLocationEnabled(): Boolean {
        val locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER

        )

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }
    private fun checkPermissions(): Boolean{
        if(ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
            ==PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    //@Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Granted",Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else{
                Toast.makeText(applicationContext, "Denied",Toast.LENGTH_SHORT).show()
            }
        }

    }
    */


    //private fun fetchCurrent
    private fun getMyData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        //val lat = "1.3521"
        //val lon = "103.8198"
        val retrofitData = retrofitBuilder.getData(latitude=lat, longitude =lon,
            api_key = "7daec42f5f875dd64c266e4a47f53151", units = "metric")
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()!!
                val currentTemp = StringBuilder()
                val currentWeather = StringBuilder()
                val currentLat = StringBuilder()
                val currentLon = StringBuilder()
                val currentCtry = StringBuilder()
                val currentWeatherDisc = StringBuilder()

                picID = responseBody.weather[0].icon
                imageView = findViewById(R.id.imageView)
                val imageName = "pic_$picID"
                val resID = resources.getIdentifier(imageName, "drawable", packageName)
                imageView.setImageResource(resID)

                currentTemp.append(responseBody.main.temp)
                currentWeather.append(responseBody.weather[0].main)
                currentWeatherDisc.append(responseBody.weather[0].description)
                currentLon.append(responseBody.coord.lon)
                currentLat.append(responseBody.coord.lat)
                currentCtry.append(responseBody.sys.country)



                //val showTextView = findViewById<TextView>(R.id.txtId)
                //showTextView.text = currentWeather
                findViewById<TextView>(R.id.CurrentWeatherId).text = "Weather: " + currentWeather
                findViewById<TextView>(R.id.CurrentWeatherDiscId).text = currentWeatherDisc.toString().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.CurrentTempId).text = "Temp: " + currentTemp.append(" °C")
                findViewById<TextView>(R.id.CurrentLonId).text = "Longitude: " + currentLon.append("° E")
                findViewById<TextView>(R.id.CurrentLatId).text = "Latitude: " + currentLat.append("° N")
                findViewById<TextView>(R.id.CurrentCtryId).text = currentCtry
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("MainActivity","onFailure: "+t.message)
            }
        })
    }

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
}
