package com.android.photos.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.photos.Models.Photo
import com.android.photos.repository.PhotosDataFactory
import javax.inject.Inject

class PhotosViewModel @Inject constructor(
    application: Application,
    private val photosDataFactory: PhotosDataFactory,
    private val pagedListConfig : PagedList.Config
) : AndroidViewModel(application) {

    var photosLiveData: LiveData<PagedList<Photo>>? = null

    fun getPhotos() {
        photosLiveData =
            LivePagedListBuilder<Int, Photo>(photosDataFactory, pagedListConfig).build()
    }
}