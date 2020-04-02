package com.android.photos.Models

import com.google.gson.annotations.SerializedName

data class Photos(
    val hits: List<Photo>
)

data class Photo (
    val id: Long,
    val type: String,
    val imageWidth : Int,
    val imageHeight : Int,
    val likes : Int,
    @SerializedName("user_id")
    val userId : Int,
    @SerializedName("user")
    val userName : String,
    // Tried largeImageURL and images are too large to fetch. Also tried previewImageURL , the images are too small and get blurred out.
    @SerializedName("webformatURL")
    val imageURL : String,
    val userImageURL : String
)