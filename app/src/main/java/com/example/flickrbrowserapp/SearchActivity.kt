package com.example.flickrbrowserapp

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.preference.PreferenceManager


class SearchActivity : BaseActivity() {

        private var  TAG = "SearchActivity"

        private var searchView : SearchView? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            Log.d(TAG,".onCreate starts")
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_search)
            activeToolbar(true)
            Log.d(TAG,".onCreate ends")
        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG,"onCreateOptionMenu : starts")
        menuInflater.inflate(R.menu.menu_search,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView?
//        searchView?.setQueryHint("test")
        val searchacbleInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchacbleInfo)
//        Log.d(TAG, searchView?.queryHint.toString())
        Log.d(TAG,".onCreateOptionMenu : $componentName")
        Log.d(TAG,".onCreateOptionMenu : hint is ${searchView?.queryHint}")
        Log.d(TAG,".onCreateOptionMenu : $searchacbleInfo")

        searchView?.isIconified = true
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG,".onQueryTextSubmit: called")
                val sharedPref=PreferenceManager.getDefaultSharedPreferences(applicationContext)
                sharedPref.edit().putString(FLICKR_QUERY,query).apply()
                searchView?.clearFocus()

                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false

            }

        })

        //METHOD CLOSE SEARCHVIEW
        searchView!!.setOnCloseListener {
            finish()
            false
        }

        Log.d(TAG,".onCreateMenu : returning ")
        return true
    }


    }
