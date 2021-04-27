package com.afrosin.appnasa.repository

import com.afrosin.appnasa.model.PictureDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") imageDateStr: String? = null
    ): Call<PictureDTO>

}