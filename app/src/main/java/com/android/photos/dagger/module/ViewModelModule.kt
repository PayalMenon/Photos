package com.android.photos.dagger.module

import androidx.lifecycle.ViewModel
import com.android.photos.dagger.ViewModelKey
import com.android.photos.ui.PhotosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PhotosViewModel::class)
    abstract fun bindPhotosViewModel(viewModel: PhotosViewModel): ViewModel
}