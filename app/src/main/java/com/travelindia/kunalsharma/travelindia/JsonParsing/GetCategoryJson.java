package com.travelindia.kunalsharma.travelindia.JsonParsing;


import android.os.AsyncTask;
import android.util.Log;

import com.travelindia.kunalsharma.travelindia.PojoClasses.Category;
import com.travelindia.kunalsharma.travelindia.DownloadStatus;
import com.travelindia.kunalsharma.travelindia.GetRawData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetCategoryJson extends AsyncTask<String, Void, List<Category>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetCategoryJson";
    private final GetCategoryJson.OnDataAvailable mCallBack;

    private List<Category> categoryList=null;

    private boolean runningOnSameThread = false;

    public GetCategoryJson(GetCategoryJson.OnDataAvailable mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface OnDataAvailable {
        void onDataAvailable(List<Category> data, DownloadStatus status);
    }


    void executeOnSameThread() {
        Log.d(TAG, "executeOnSameThread starts");
        runningOnSameThread = true;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute("http://www.yaaranasafar.pe.hu/AndroidBackend/index.php/GenerateJson");

        Log.d(TAG, "executeOnSameThread ends");
    }



    @Override
    protected void onPostExecute(List<Category> photos) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(categoryList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected List<Category> doInBackground(String... params) {
        Log.d(TAG, "doInBackground starts");

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread("http://www.yaaranasafar.pe.hu/AndroidBackend/index.php/GenerateJson");
        Log.d(TAG, "doInBackground ends");
        return categoryList;
    }



    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);

        if(status == DownloadStatus.OK) {
            categoryList=new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);

                JSONArray category = jsonData.getJSONArray("category");

                for(int i=0; i<category.length(); i++) {
                    JSONObject jsonPlace = category.getJSONObject(i);

                    int Catid = jsonPlace.getInt("Catid");
                    String Catname = jsonPlace.getString("Catname");
                    String Catthumbnail = jsonPlace.getString("Catthumbnail");

                    Category catObject = new Category(Catid,Catname,Catthumbnail);
                    categoryList.add(catObject);

                    Log.d(TAG, "onDownloadComplete " + catObject.toString());
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
            mCallBack.onDataAvailable(categoryList, status);
        }
        Log.d(TAG, "onDownloadComplete ends");
    }
}
