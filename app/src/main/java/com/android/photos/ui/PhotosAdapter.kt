package com.android.photos.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.photos.Models.Photo
import com.android.photos.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.photo_layout.view.*

class PhotosAdapter : PagedListAdapter<Photo, PhotoViewHolder>(PhotoDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let { photo ->
            holder.bindView(photo)
        }
    }
}

class PhotoViewHolder(private val view : View) : RecyclerView.ViewHolder(view){

    fun bindView(photo: Photo) {
        view.photo_user.text = view.resources.getText(R.string.photo_by).toString().plus(photo.userName)
        view.photo_likes.text = view.resources.getText(R.string.likes).toString().plus(photo.likes)
        if(!photo.imageURL.isEmpty()) {
            Glide.with(view.context).load(photo.imageURL).placeholder(R.drawable.placeholder).into(view.photo_image)
        }
       if(!photo.userImageURL.isEmpty())  {
           Glide.with(view.context).load(photo.userImageURL).into(view.photo_user_image)
       }
    }
}