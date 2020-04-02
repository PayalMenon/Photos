package com.android.photos.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.android.photos.Models.Photo
import com.android.photos.Models.Photos
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PhotosDataSource @Inject constructor(private val apiService: ApiService) :
    PageKeyedDataSource<Int, Photo>() {

    companion object {
        private const val NETWORK_ERROR = "No Network to fetch request"
        private const val REQUEST_FAILED = "API Failed to give response"
        private const val PIX = "PIX_Photos"
    }
 var errorMessage :  MutableLiveData<String> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Photo>
    ) {
        try {
            val call: Call<Photos> = apiService.getPhotosList(1)
            val result: Response<Photos> = call.execute()
            if (result.isSuccessful) {
                result.body()?.let { photos: Photos ->
                    callback.onResult(photos.hits, 0, 2)
                }
            } else {
                errorMessage.postValue("REQUEST_FAILED")

                Log.d(PIX, REQUEST_FAILED)
            }
        } catch (exception: IOException) {
            Log.d(PIX, NETWORK_ERROR)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        try {
            val call: Call<Photos> = apiService.getPhotosList(params.key)
            val result: Response<Photos> = call.execute()
            if (result.isSuccessful) {
                result.body()?.let { photos: Photos ->
                    callback.onResult(photos.hits, params.key + 1)
                }
            } else {
                Log.d(PIX, REQUEST_FAILED)
            }
        } catch (exception: IOException) {
            Log.d(PIX, NETWORK_ERROR)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Photo>) {
        try {
            val call: Call<Photos> = apiService.getPhotosList(params.key)
            val result: Response<Photos> = call.execute()
            if (result.isSuccessful) {
                result.body()?.let { photos: Photos ->
                    callback.onResult(photos.hits, if (params.key > 1) params.key - 1 else 0)
                }
            } else {
                Log.d(PIX, REQUEST_FAILED)
            }
        } catch (exception: IOException) {
            Log.d(PIX, NETWORK_ERROR)
        }
    }

}