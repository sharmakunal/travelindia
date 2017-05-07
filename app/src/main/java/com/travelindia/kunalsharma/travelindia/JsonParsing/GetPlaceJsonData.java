package com.travelindia.kunalsharma.travelindia.JsonParsing;

import android.os.AsyncTask;
import android.util.Log;

import com.travelindia.kunalsharma.travelindia.DownloadStatus;
import com.travelindia.kunalsharma.travelindia.GetRawData;
import com.travelindia.kunalsharma.travelindia.PojoClasses.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal sharma on 31-Mar-17.
 */

public class GetPlaceJsonData extends AsyncTask<String, Void, List<Place>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetTravelJsonData";
    private final OnDataAvailable mCallBack;
    private List<Place> mPlaceList = null;
    private String url;
    private boolean runningOnSameThread = false;

    public GetPlaceJsonData(OnDataAvailable mCallBack,String type,String id){
        this.mCallBack = mCallBack;
        url= "http://www.yaaranasafar.pe.hu/AndroidBackend/index.php/"+type+"/view_json/"+id ;
    }

    public interface OnDataAvailable {
        void onDataAvailable(List<Place> data, DownloadStatus status);
    }



    void executeOnSameThread() {
        Log.d(TAG, "executeOnSameThread starts");
        runningOnSameThread = true;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(url);

        Log.d(TAG, "executeOnSameThread ends");
    }



    @Override
    protected void onPostExecute(List<Place> photos) {
        Log.d(TAG, "onPostExecute starts" + url);

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mPlaceList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected List<Place> doInBackground(String... params) {
        Log.d(TAG, "doInBackground starts");
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(url);
        Log.d(TAG, "doInBackground ends");
        return mPlaceList;
    }



    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);

        if(status == DownloadStatus.OK) {
            mPlaceList = new ArrayList<>();

                    try {
                JSONObject jsonData = new JSONObject(data);

                JSONArray article = jsonData.getJSONArray("Place");


                for(int i=0; i<article.length(); i++) {
                    JSONObject jsonPlace = article.getJSONObject(i);
                        //int Catid = jsonPlace.getInt("Catid");

                   // if(String.valueOf(Catid).equals(PlaceListActivity.cat_id)) {
                        String Pname = jsonPlace.getString("Pname");
                        String Pthumbnail = jsonPlace.getString("Pthumbnail");
                        String Pthumbnailinfo = jsonPlace.getString("Pthumbnailinfo");
                        String Pinfo = jsonPlace.getString("Pinfo");
                        String Pcity = jsonPlace.getString("Pcity");
                        String Pstate = jsonPlace.getString("PState");
                        String PCountry = jsonPlace.getString("PCountry");
                        String Pnearby = jsonPlace.getString("Pnearby");


                        Place    placeObject = new Place(Pname, Pthumbnail, Pthumbnailinfo, Pinfo, Pcity, Pstate, PCountry, Pnearby);
                        mPlaceList.add(placeObject);
                        Log.d(TAG, "onDownloadComplete " + placeObject.toString());
                  //  }


                }
            } catch(JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        if(runningOnSameThread && mCallBack != null) {
            // now inform the caller that processing is done - possibly returning null if there
            // was an error
            mCallBack.onDataAvailable(mPlaceList, status);
        }
        Log.d(TAG, "onDownloadComplete ends");
    }
}
