package com.android.photos.dagger.module

import androidx.paging.PagedList
import com.android.photos.BuildConfig
import com.android.photos.repository.ApiService
import com.android.photos.repository.PhotosDataFactory
import com.android.photos.repository.PhotosDataSource
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val PIX_BASE_URL = "https://pixabay.com/api/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val url: HttpUrl = original.url().newBuilder()
                .addQueryParameter("key", BuildConfig.PIX_API_KEY)
                .build()

            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        }
        val newBuilder = httpBuilder.build().newBuilder().addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)
        return newBuilder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofitService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PIX_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoDataSource(apiService: ApiService): PhotosDataSource {
        return PhotosDataSource(apiService)
    }

    @Provides
    @Singleton
    fun providePhotoDataFactory(photoDataSource: PhotosDataSource): PhotosDataFactory {
        return PhotosDataFactory(photoDataSource)
    }

    @Provides
    @Singleton
    fun providesPagedConfig() : PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10).build()
    }
}