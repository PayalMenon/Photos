package com.android.photos.dagger.module

import com.android.photos.dagger.ViewModelBuilder
import com.android.photos.ui.PhotosActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun startActivity(): PhotosActivity
}