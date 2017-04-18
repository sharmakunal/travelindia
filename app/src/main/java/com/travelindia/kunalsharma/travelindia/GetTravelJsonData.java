package com.travelindia.kunalsharma.travelindia;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.StreamHandler;

/**
 * Created by kunal sharma on 31-Mar-17.
 */

public class GetTravelJsonData extends AsyncTask<String, Void, List<Place>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetTravelJsonData";
    private final OnDataAvailable mCallBack;
    private List<Place> mPlaceList = null;

    private boolean runningOnSameThread = false;
    //Place placeObject;

    public GetTravelJsonData(OnDataAvailable mCallBack) {
        this.mCallBack = mCallBack;
    }

    interface OnDataAvailable {
        void onDataAvailable(List<Place> data, DownloadStatus status);
    }


    void executeOnSameThread() {
        Log.d(TAG, "executeOnSameThread starts");
        runningOnSameThread = true;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute("http://www.yaaranasafar.pe.hu/AndroidBackend/index.php/GenerateJson");

        Log.d(TAG, "executeOnSameThread ends");
    }



    @Override
    protected void onPostExecute(List<Place> photos) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mPlaceList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected List<Place> doInBackground(String... params) {
        Log.d(TAG, "doInBackground starts");
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread("http://www.yaaranasafar.pe.hu/AndroidBackend/index.php/GenerateJson");
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

                JSONArray article = jsonData.getJSONArray("article");


                for(int i=0; i<article.length(); i++) {
                    JSONObject jsonPlace = article.getJSONObject(i);
                    int Catid = jsonPlace.getInt("Catid");

                    if(String.valueOf(Catid).equals(PlaceListActivity.cat_id)) {
                        String Pname = jsonPlace.getString("Pname");
                        String Pthumbnail = jsonPlace.getString("Pthumbnail");
                        String Pthumbnailinfo = jsonPlace.getString("Pthumbnailinfo");
                        String Pinfo = jsonPlace.getString("Pinfo");
                        String Pcity = jsonPlace.getString("Pcity");
                        String Pstate = jsonPlace.getString("PState");
                        String PCountry = jsonPlace.getString("PCountry");
                        String Pnearby = jsonPlace.getString("Pnearby");


                        Place    placeObject = new Place(Catid, Pname, Pthumbnail, Pthumbnailinfo, Pinfo, Pcity, Pstate, PCountry, Pnearby);
                        mPlaceList.add(placeObject);
                        Log.d(TAG, "onDownloadComplete " + placeObject.toString());
                    }


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
