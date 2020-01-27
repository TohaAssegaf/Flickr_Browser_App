package com.example.flickrbrowserapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BaseActivity(), GetRawData.OnDownloadComplete, GetFlickrJsonData.OnDataAvailable
    , RecyclerItemClickListener.OnRecyclerClickListener {
    private val TAG="MainActivity"

    private val flickrRecyclerViewAdapter=FlickrRecyclerViewAdapter(ArrayList())
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate called")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activeToolbar(false)



        recycler_view.layoutManager= LinearLayoutManager(this)
        recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,recycler_view,this))
        recycler_view.adapter= flickrRecyclerViewAdapter

        Log.d(TAG,"onCreate ends")
    }
    override fun onItemClick(v: View, position: Int) {
        Log.d(TAG,".onItemClick : starts")
        Toast.makeText(this,"Normal tap at postion : $position",Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(v: View, position: Int) {
        Log.d(TAG,".onItemLongClick : starts")
//        Toast.makeText(this,"Long tap at postion : $position",Toast.LENGTH_SHORT).show()
        val photo=flickrRecyclerViewAdapter.getPhoto(position)
        if(photo!=null){
            val intent=Intent(this,PhotoDetailActivity::class.java)
            intent.putExtra(PHOTO_TRANSFER,photo)
            startActivity(intent)
        }
    }

    private fun createUri(baseURL: String, searchCriteria: String, critics_pick: String, matchAll: Boolean): String? {
        Log.d(TAG,"createURI starts")
        return Uri.parse(baseURL).buildUpon().
            appendQueryParameter("query",searchCriteria).
            appendQueryParameter("critics-pick",critics_pick).
            appendQueryParameter("format","json").
            appendQueryParameter("nojsoncallback","1").
            build().toString()



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG,"onCreateOptionMenu called")
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d(TAG,"onOptionsItemSelected called")

        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object{
        private const val TAG="MainActivity"
    }

    override fun onDownloadComplete(data:String,status:DownloadStatus){
        if(status==DownloadStatus.OK){
            Log.d(TAG,"onDownloadCompleted called")
            val getFlickrJsonData=GetFlickrJsonData(this)
            getFlickrJsonData.execute(data)
        }else{
            Log.d(TAG,"onDownloadComplete failed with status $status. Error message is : $data")
        }
    }

    override fun onDataAvailable(data: List<Photo>) {
        Log.d(TAG,"onDataAvailable called, data is $data")
        flickrRecyclerViewAdapter.loadNewData(data)
        Log.d(TAG,"onDataAvailable ends")
    }

    override fun onError(exception: Exception) {
        Log.e(TAG,"onError called with ${exception.message}")
    }

    override fun onResume() {
        Log.d(TAG,"onResume starts")
        super.onResume()

        //shared pref data show
        val sharedPref= PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val queryResult=sharedPref.getString(FLICKR_QUERY,"")

        if(queryResult!!.isNotEmpty()){
            val url=createUri("https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=2xuyfE08ByW8ncySkrjeGZRGz8sh6bbE",queryResult,"0",true)
            val getRawData=GetRawData(this)
            getRawData.execute(url)

            Log.d(TAG,url.toString())

        }
        Log.d(TAG,".onResume : ends")
    }




}
