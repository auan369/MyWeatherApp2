package com.example.myweatherapp2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("data/2.5/weather?") //lat=1.3521&lon=103.8198&appid=7daec42f5f875dd64c266e4a47f53151&units=metric
    fun getData(
        @Query ("lat") latitude:String,
        @Query ("lon") longitude:String,
        @Query ("appid") api_key:String,
        @Query ("units") units:String,

    ): Call<MyData>
}