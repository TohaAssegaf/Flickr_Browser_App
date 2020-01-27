package com.example.flickrbrowserapp

//import androidx.widget.Toolbar
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

internal const val FLICKR_QUERY="FLICKR_QUERY"
internal const val PHOTO_TRANSFER="PHOTO_TRANSFER"

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private val TAG="BaseActivity"

    internal fun activeToolbar(enableHome : Boolean){
        Log.d(TAG,".activeToolbar")

        var toolbar=findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}