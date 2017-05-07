package com.travelindia.kunalsharma.travelindia.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.travelindia.kunalsharma.travelindia.PojoClasses.Category;
import com.travelindia.kunalsharma.travelindia.Adapter.CategoryRecylerViewAdapter;
import com.travelindia.kunalsharma.travelindia.DownloadStatus;
import com.travelindia.kunalsharma.travelindia.JsonParsing.GetCategoryJson;
import com.travelindia.kunalsharma.travelindia.Activities.PlaceListActivity;
import com.travelindia.kunalsharma.travelindia.R;
import com.travelindia.kunalsharma.travelindia.RecyclerItemClickListener;
import java.util.ArrayList;
import java.util.List;
import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Categories extends Fragment implements GetCategoryJson.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener{


    public Categories() {
        // Required empty public constructor
    }


    private CategoryRecylerViewAdapter mcategoryRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragement_home, container, false);
        RecyclerView recyclerView;

        GetCategoryJson gettraveljsondata = new GetCategoryJson(this);
        gettraveljsondata.execute();

        recyclerView = (RecyclerView)root.findViewById(R.id.recycler_view_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, this));

        mcategoryRecyclerViewAdapter = new CategoryRecylerViewAdapter(getActivity(), new ArrayList<Category>());
        recyclerView.setAdapter(mcategoryRecyclerViewAdapter);

         return  root;
    }


    @Override
    public void onDataAvailable(List<Category> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK) {
            mcategoryRecyclerViewAdapter.loadNewData(data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Intent intent = new Intent(getActivity(), PlaceListActivity.class);
        intent.putExtra("PHOTO_TRANSFER",mcategoryRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);
    }

}
