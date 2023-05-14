package com.example.myweatherapp2


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.time.LocalDateTime



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
        var currentTemp = "32.62"
        var currentWeather = "Clouds"
        //var currentLat = "1.3521"
        //var currentLon = "103.82"
        var currentCtry = "SG"
        var currentLoc = "Singapore"
        var currentWeatherDesc = "Few clouds"
        var timeStamp = "2023-05-14 09:40:41.966"

    }





    private lateinit var imageView : ImageView
    //@RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClick = findViewById<Button>(R.id.newPage)
        buttonClick.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
        }





        title = "KotlinApp"
        val button: Button = findViewById(R.id.getLocation)
        button.setOnClickListener {
            getLocation()
            saveData()

        }

        //My Interface code
        loadData()
        getLocation()
        getMyData()
        saveData()
    }

    fun timenow() {
        val current = LocalDateTime.now()
        //println("Current Date and Time is: $current")
        timeStamp = current.toLocalDate().toString() + "\t" + current.toLocalTime().toString()
        findViewById<TextView>(R.id.TimeId).text = "Retrieved:\t" + timeStamp
    }



    private fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("LAT_KEY", lat)
            putString("LON_KEY", lon)

            putString("PICID_KEY", picID)

            putString("TEMP_KEY", currentTemp)
            putString("WEATHER_KEY", currentWeather)
            putString("CTRY_KEY", currentCtry)
            putString("WEATHERDESC_KEY", currentWeatherDesc)
            putString("TIME_KEY", timeStamp)
            putString("LOC_KEY", currentLoc)
        }.apply()
        Toast.makeText(this,"Data saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val latsavedString = sharedPreferences.getString("LAT_KEY",null)
        val lonsavedString = sharedPreferences.getString("LON_KEY",null)
        val picidsavedString = sharedPreferences.getString("PICID_KEY",null)
        val tempsavedString = sharedPreferences.getString("TEMP_KEY",null)
        val weathersavedString = sharedPreferences.getString("WEATHER_KEY",null)
        val ctrysavedString = sharedPreferences.getString("CTRY_KEY",null)
        val weatherdescsavedString = sharedPreferences.getString("WEATHERDESC_KEY",null)
        val timesavedString = sharedPreferences.getString("TIME_KEY",null)
        val locsavedString = sharedPreferences.getString("LOC_KEY",null)
        findViewById<TextView>(R.id.TimeId).text = "Retrieved:\t" + timesavedString
        findViewById<TextView>(R.id.CurrentWeatherId).text = "Weather: " + weathersavedString
        findViewById<TextView>(R.id.CurrentWeatherDiscId).text = weatherdescsavedString.toString().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        findViewById<TextView>(R.id.CurrentTempId).text = "Temp: " + tempsavedString + " °C"
        findViewById<TextView>(R.id.CurrentLonId).text = "Longitude: " + lonsavedString +"° E"
        findViewById<TextView>(R.id.CurrentLatId).text = "Latitude: " + latsavedString +"° N"
        findViewById<TextView>(R.id.CurrentCtryId).text = ctrysavedString + ": "+ locsavedString
        imageView = findViewById(R.id.imageView)
        val imageName = "pic_$picidsavedString"
        val resID = resources.getIdentifier(imageName, "drawable", packageName)
        imageView.setImageResource(resID)
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


                picID = responseBody.weather[0].icon
                imageView = findViewById(R.id.imageView)
                val imageName = "pic_$picID"
                val resID = resources.getIdentifier(imageName, "drawable", packageName)
                imageView.setImageResource(resID)

                currentTemp = responseBody.main.temp.toString()
                currentWeather = responseBody.weather[0].main.toString()
                currentWeatherDesc = responseBody.weather[0].description.toString()
                lon = responseBody.coord.lon.toString()
                lat = responseBody.coord.lat.toString()
                currentCtry = responseBody.sys.country.toString()
                currentLoc = responseBody.name.toString()



                //val showTextView = findViewById<TextView>(R.id.txtId)
                //showTextView.text = currentWeather
                findViewById<TextView>(R.id.CurrentWeatherId).text = "Weather: " + currentWeather
                findViewById<TextView>(R.id.CurrentWeatherDiscId).text = currentWeatherDesc.toString().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.CurrentTempId).text = "Temp: " + currentTemp + " °C"
                findViewById<TextView>(R.id.CurrentLonId).text = "Longitude: " + lon +"° E"
                findViewById<TextView>(R.id.CurrentLatId).text = "Latitude: " + lat +"° N"
                findViewById<TextView>(R.id.CurrentCtryId).text = currentCtry + ": " + currentLoc
                try{timenow()}
                catch (e: NoClassDefFoundError){
                    println("Something went wrong.")
                }
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
