package com.travelindia.kunalsharma.travelindia.JsonParsing;

/**
 * Created by kunal sharma on 01-May-17.
 */

import android.os.AsyncTask;
import android.util.Log;
import com.travelindia.kunalsharma.travelindia.DownloadStatus;
import com.travelindia.kunalsharma.travelindia.GetRawData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import com.travelindia.kunalsharma.travelindia.PojoClasses.State;


public class GetStateJson extends AsyncTask<String, Void, List<State>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetStateJson";
    private final GetStateJson.OnDataAvailable mCallBack;

    private List<State> StateList=null;

    private boolean runningOnSameThread = false;

    public GetStateJson(GetStateJson.OnDataAvailable mCallBack) {
        this.mCallBack = mCallBack;
    }

    public interface OnDataAvailable {
        void onDataAvailable(List<State> data, DownloadStatus status);
    }


    void executeOnSameThread() {
        Log.d(TAG, "executeOnSameThread starts");
        runningOnSameThread = true;

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute("http://www.yaaranasafar.pe.hu/AndroidBackend/index.php/GenerateJson");

        Log.d(TAG, "executeOnSameThread ends");
    }



    @Override
    protected void onPostExecute(List<State> photos) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(StateList, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected List<State> doInBackground(String... params) {
        Log.d(TAG, "doInBackground starts");

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread("http://www.yaaranasafar.pe.hu/AndroidBackend/index.php/StateJson");
        Log.d(TAG, "doInBackground ends");
        return StateList;
    }



    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);

        if(status == DownloadStatus.OK) {
            StateList=new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);

                JSONArray state = jsonData.getJSONArray("state");

                for(int i=0; i<state.length(); i++) {
                    JSONObject jsonPlace = state.getJSONObject(i);

                    int Stateid = jsonPlace.getInt("Sid");
                    String Statename = jsonPlace.getString("Sname");
                    String Statethumbnail = jsonPlace.getString("Sthumbnail");
                    String Statethumbnailinfo = jsonPlace.getString("Sinfo");

                    State StateObject = new State(Stateid,Statename,Statethumbnail,Statethumbnailinfo);
                    StateList.add(StateObject);

                    Log.d(TAG, "onDownloadComplete " + StateObject.toString());
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
            mCallBack.onDataAvailable(StateList, status);
        }
        Log.d(TAG, "onDownloadComplete ends");
    }
}

