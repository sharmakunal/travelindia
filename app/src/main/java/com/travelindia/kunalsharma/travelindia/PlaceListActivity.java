package com.travelindia.kunalsharma.travelindia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.travelindia.kunalsharma.travelindia.Adapter.PlaceRecyclerViewAdapter;
import com.travelindia.kunalsharma.travelindia.JsonParsing.GetPlaceJsonData;
import com.travelindia.kunalsharma.travelindia.PojoClasses.Category;
import com.travelindia.kunalsharma.travelindia.PojoClasses.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceListActivity extends BaseActivity implements GetPlaceJsonData.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener{

    private static final String TAG = "PlaceList Activity";
    private PlaceRecyclerViewAdapter mPlaceRecyclerViewAdapter;
    public static String cat_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);
        activateToolbar(true);



        Intent intent = getIntent();
        Category cat = (Category) intent.getSerializableExtra(PHOTO_TRANSFER);
        int id = cat.getCatid();
        cat_id = String.valueOf(id);


        RecyclerView recyclerView;

        GetPlaceJsonData gettraveljsondata = new GetPlaceJsonData(this,cat_id);
        gettraveljsondata.execute();

        recyclerView = (RecyclerView)findViewById(R.id.recyler_place_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        mPlaceRecyclerViewAdapter = new PlaceRecyclerViewAdapter(this, new ArrayList<Place>());
        recyclerView.setAdapter(mPlaceRecyclerViewAdapter);

   }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        return true;

        //MenuItem search = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        //search(searchView);
    }

    @Override
    public void onDataAvailable(List<Place> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK) {
            mPlaceRecyclerViewAdapter.loadNewData(data);
        } else {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }

        Log.d(TAG, "onDataAvailable: ends");
    }


    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
       // Toast.makeText(this, "Normal tap at position " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PlaceDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER,mPlaceRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

               // mPlaceRecyclerViewAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
