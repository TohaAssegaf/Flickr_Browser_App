package com.example.flickrbrowserapp

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class GetFlickrJsonData(private val listener:OnDataAvailable):AsyncTask<String,Void,ArrayList<Photo>>() {
    private val TAG="GetFlickrJsonData"

        interface OnDataAvailable{
            fun onDataAvailable(data:List<Photo>)
            fun onError(exception:Exception)
        }

    override fun doInBackground(vararg params: String?): ArrayList<Photo> {
        Log.d(TAG, "doInBackground Starts")

        val photoList=ArrayList<Photo>()

        try {
            val jsonData=JSONObject(params[0])
            val itemsArray=jsonData.getJSONArray("results")

            for(i in 0 until itemsArray.length()){
                val jsonPhoto=itemsArray.getJSONObject(i)
                Log.d("ssssssssss", jsonPhoto.getString("multimedia"))
                val displayTitle=jsonPhoto.getString("display_title")
                val reviewer=jsonPhoto.getString("summary_short")
                val criticsPick=jsonPhoto.getString("critics_pick")
                val query=jsonPhoto.getString("headline")

                var photoObject: Photo? = null
                if (jsonPhoto.getString("multimedia")!="null") {
                    val jsonMedia = jsonPhoto.getJSONObject("multimedia")
                    val photoUrl = jsonMedia.getString("src")
//                    val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")


                    photoObject = Photo(displayTitle, reviewer, criticsPick, photoUrl, query, photoUrl)
                    Log.d(TAG, "doInBackground $photoObject")
//                    Log.d(TAG,link)
                    photoList.add(photoObject)
                }

            }
        }catch (e:JSONException){
            e.printStackTrace()
            Log.e(TAG,".doInBackground : Error processing Json data. ${e.message}")
            cancel(true)
            listener.onError(e)
        }
        Log.d(TAG,"doInBackground ends")
        return photoList
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        Log.d(TAG,"onPostExecute Starts")
        super.onPostExecute(result)
        listener.onDataAvailable(result)
        Log.d(TAG,"onPostExecute ends")
    }
}
