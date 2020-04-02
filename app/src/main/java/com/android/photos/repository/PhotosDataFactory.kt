package com.android.photos.repository

import androidx.paging.DataSource
import com.android.photos.Models.Photo
import javax.inject.Inject

class PhotosDataFactory  @Inject constructor(private val photosDataSource: PhotosDataSource) : DataSource.Factory<Int, Photo>() {

    override fun create(): DataSource<Int, Photo> {
        return photosDataSource
    }

}