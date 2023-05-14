package com.example.myweatherapp2
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.Objects



class NewActivity:  ComponentActivity() {

    //private lateinit var imageView : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_activity)

        val backButtonClick = findViewById<Button>(R.id.backButton)
        backButtonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        getNYData()
        getSGData()
        getMUMData()
        getDELData()
        getSYDData()
        getMELData()


    }

    private fun getNYData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface2::class.java)
        //val lat = "1.3521"
        //val lon = "103.8198"
        val retrofitData = retrofitBuilder.getData(id = "5128638",
            api_key = "7daec42f5f875dd64c266e4a47f53151", units = "metric")
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()!!


                val picID = responseBody.weather[0].icon

                val imageName = "pic_${picID}"
                var imageView : ImageView = findViewById(R.id.NYimageView)
                imageView.setImageResource(resources.getIdentifier(imageName, "drawable", packageName))


                findViewById<TextView>(R.id.NYWeatherId).text = "Weather: " + responseBody.weather[0].main.toString()
                findViewById<TextView>(R.id.NYWeatherDiscId).text = responseBody.weather[0].description.toString().toString().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.NYTempId).text = "Temp: " + responseBody.main.temp.toString() + " °C"
                findViewById<TextView>(R.id.NYCtryId).text = responseBody.sys.country.toString() + ": New York"
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("NewActivity","onFailure: "+t.message)
            }
        })
    }



    private fun getSGData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface2::class.java)
        //val lat = "1.3521"
        //val lon = "103.8198"
        val retrofitData = retrofitBuilder.getData(id = "1880252",
            api_key = "7daec42f5f875dd64c266e4a47f53151", units = "metric")
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()!!


                val picID = responseBody.weather[0].icon

                val imageName = "pic_${picID}"
                var imageView : ImageView = findViewById(R.id.SGimageView)
                imageView.setImageResource(resources.getIdentifier(imageName, "drawable", packageName))


                findViewById<TextView>(R.id.SGWeatherId).text = "Weather: " + responseBody.weather[0].main.toString()
                findViewById<TextView>(R.id.SGWeatherDiscId).text = responseBody.weather[0].description.toString().toString().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.SGTempId).text = "Temp: " + responseBody.main.temp.toString() + " °C"
                findViewById<TextView>(R.id.SGCtryId).text = responseBody.sys.country.toString() + ": Singapore"
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("NewActivity","onFailure: "+t.message)
            }
        })
    }


    private fun getMUMData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface2::class.java)
        //val lat = "1.3521"
        //val lon = "103.8198"
        val retrofitData = retrofitBuilder.getData(id = "1275339",
            api_key = "7daec42f5f875dd64c266e4a47f53151", units = "metric")
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()!!


                val picID = responseBody.weather[0].icon

                val imageName = "pic_${picID}"
                var imageView : ImageView = findViewById(R.id.MUMimageView)
                imageView.setImageResource(resources.getIdentifier(imageName, "drawable", packageName))


                findViewById<TextView>(R.id.MUMWeatherId).text = "Weather: " + responseBody.weather[0].main.toString()
                findViewById<TextView>(R.id.MUMWeatherDiscId).text = responseBody.weather[0].description.toString().toString().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.MUMTempId).text = "Temp: " + responseBody.main.temp.toString() + " °C"
                findViewById<TextView>(R.id.MUMCtryId).text = responseBody.sys.country.toString() + ": Mumbai"
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("NewActivity","onFailure: "+t.message)
            }
        })
    }

    private fun getDELData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface2::class.java)
        //val lat = "1.3521"
        //val lon = "103.8198"
        val retrofitData = retrofitBuilder.getData(id = "1273294",
            api_key = "7daec42f5f875dd64c266e4a47f53151", units = "metric")
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()!!


                val picID = responseBody.weather[0].icon

                val imageName = "pic_${picID}"
                var imageView : ImageView = findViewById(R.id.DELimageView)
                imageView.setImageResource(resources.getIdentifier(imageName, "drawable", packageName))


                findViewById<TextView>(R.id.DELWeatherId).text = "Weather: " + responseBody.weather[0].main.toString()
                findViewById<TextView>(R.id.DELWeatherDiscId).text = responseBody.weather[0].description.toString().toString().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.DELTempId).text = "Temp: " + responseBody.main.temp.toString() + " °C"
                findViewById<TextView>(R.id.DELCtryId).text = responseBody.sys.country.toString() + ": Delhi"
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("NewActivity","onFailure: "+t.message)
            }
        })
    }

    private fun getSYDData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface2::class.java)
        //val lat = "1.3521"
        //val lon = "103.8198"
        val retrofitData = retrofitBuilder.getData(id = "2147714",
            api_key = "7daec42f5f875dd64c266e4a47f53151", units = "metric")
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()!!


                val picID = responseBody.weather[0].icon

                val imageName = "pic_${picID}"
                var imageView : ImageView = findViewById(R.id.SYDimageView)
                imageView.setImageResource(resources.getIdentifier(imageName, "drawable", packageName))


                findViewById<TextView>(R.id.SYDWeatherId).text = "Weather: " + responseBody.weather[0].main.toString()
                findViewById<TextView>(R.id.SYDWeatherDiscId).text = responseBody.weather[0].description.toString().toString().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.SYDTempId).text = "Temp: " + responseBody.main.temp.toString() + " °C"
                findViewById<TextView>(R.id.SYDCtryId).text = responseBody.sys.country.toString() + ": Sydney"
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("NewActivity","onFailure: "+t.message)
            }
        })
    }
    private fun getMELData(){
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface2::class.java)
        //val lat = "1.3521"
        //val lon = "103.8198"
        val retrofitData = retrofitBuilder.getData(id = "2158177",
            api_key = "7daec42f5f875dd64c266e4a47f53151", units = "metric")
        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                val responseBody = response.body()!!


                val picID = responseBody.weather[0].icon

                val imageName = "pic_${picID}"
                var imageView : ImageView = findViewById(R.id.MELimageView)
                imageView.setImageResource(resources.getIdentifier(imageName, "drawable", packageName))


                findViewById<TextView>(R.id.MELWeatherId).text = "Weather: " + responseBody.weather[0].main.toString()
                findViewById<TextView>(R.id.MELWeatherDiscId).text = responseBody.weather[0].description.toString().toString().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.MELTempId).text = "Temp: " + responseBody.main.temp.toString() + " °C"
                findViewById<TextView>(R.id.MELCtryId).text = responseBody.sys.country.toString() + ": Melbourne"
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("NewActivity","onFailure: "+t.message)
            }
        })
    }
}