package com.android.photos.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.photos.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class PhotosActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var photosViewModel: PhotosViewModel
    private val adapter = PhotosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        photosViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PhotosViewModel::class.java)
        this.photos_list.adapter = adapter

        photosViewModel.getPhotos()

        photosViewModel.photosLiveData?.observe(this, Observer { list ->
            this.loading.visibility = View.GONE
            this.photos_list.visibility = View.VISIBLE
            adapter.submitList(list)
        })
    }
}
