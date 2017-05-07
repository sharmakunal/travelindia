package com.travelindia.kunalsharma.travelindia.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.travelindia.kunalsharma.travelindia.Activities.PlaceAccordingToStateActivity;
import com.travelindia.kunalsharma.travelindia.Adapter.CustomAdapter;
import com.travelindia.kunalsharma.travelindia.DownloadStatus;
import com.travelindia.kunalsharma.travelindia.JsonParsing.GetStateJson;
import com.travelindia.kunalsharma.travelindia.PojoClasses.State;
import com.travelindia.kunalsharma.travelindia.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class States extends Fragment implements GetStateJson.OnDataAvailable{


    public States() {
        // Required empty public constructor
    }


    private CustomAdapter mstateViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_state, container, false);

        ArrayList<State>  record = new ArrayList<>();
        GridView gridview;

        GetStateJson getstatejsondata = new GetStateJson(this);
        getstatejsondata.execute();

        mstateViewAdapter=new CustomAdapter(getContext(),record);
        gridview = (GridView) root.findViewById(R.id.customgrid);
        gridview.setAdapter(mstateViewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                State c = mstateViewAdapter.getItem(position);
                int a = c.getStateid();

                //Toast.makeText(getActivity(), "You Clicked "+ a, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getActivity(),PlaceAccordingToStateActivity.class);
                intent.putExtra("PLACE_TRANSFER",mstateViewAdapter.getItem(position));
                startActivity(intent);
            }
        });
        return root;
    }


    @Override
    public void onDataAvailable(List<State> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK) {
            for(State s : data){
                Log.d(TAG, "onDataAvailable: "+ s.toString());

            }
            mstateViewAdapter.loadNewData(data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: ends");
    }
}
