package com.example.flickrbrowserapp

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo_detail.*
import kotlinx.android.synthetic.main.content_photo_detail.*

class PhotoDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        setSupportActionBar(toolbar)

        activeToolbar(true)

        val photo=intent.extras?.getParcelable<Photo>(PHOTO_TRANSFER) as Photo
//        val photo=intent.getSerializableExtra(PHOTO_TRANSFER) as Photo
        photo_title.text=resources.getString(R.string.photo_title_text, photo.display_title)
        photo_author.text=resources.getString(R.string.photo_author_text,"yes",photo.reviewer)
        photo_tags.text=resources.getString(R.string.photo_tags_text, photo.query)

        Picasso.with(this)
            .load(photo.link)//load an image from url
            .placeholder(R.drawable.placeholder)//yee
            .error(R.drawable.placeholder)
            .into(photo_image)



    }

}
