package com.android.photos.repository

import com.android.photos.Models.Photos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?image_type=photo")
    fun getPhotosList(
        @Query("page") page: Int
    ): Call<Photos>
}