package com.travelindia.kunalsharma.travelindia.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.travelindia.kunalsharma.travelindia.Adapter.PlaceGridViewAdapter;
import com.travelindia.kunalsharma.travelindia.DownloadStatus;
import com.travelindia.kunalsharma.travelindia.JsonParsing.GetPlaceJsonData;
import com.travelindia.kunalsharma.travelindia.PojoClasses.Place;
import com.travelindia.kunalsharma.travelindia.PojoClasses.State;
import com.travelindia.kunalsharma.travelindia.R;
import java.util.ArrayList;
import java.util.List;

public class PlaceAccordingToStateActivity extends AppCompatActivity implements GetPlaceJsonData.OnDataAvailable{


    private static final String TAG = "PlaceList Activity";
    private PlaceGridViewAdapter mPlaceGridViewViewAdapter;
    private String stateid = "";
    private String type="PlaceOnBasisOfState";

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_according_to_state);

        Intent intent = getIntent();
        State state = (State) intent.getSerializableExtra("PLACE_TRANSFER");

        int id = state.getStateid();
        stateid = String.valueOf(id);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.state_collapsing_toolbar);


        dynamicToolbarColor();

        String title = state.getStateName();


        ImageView photoImage = (ImageView) findViewById(R.id.statethumbnail);
        Picasso.with(this).load(state.getStatethumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);

        ArrayList<Place> record = new ArrayList<>();
        GridView gridview;

        GetPlaceJsonData getplacejsondata = new GetPlaceJsonData(this,type,stateid);
        getplacejsondata.execute();

        mPlaceGridViewViewAdapter =new PlaceGridViewAdapter(this,record);
        gridview = (GridView) findViewById(R.id.placegridview);
        gridview.setAdapter(mPlaceGridViewViewAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PlaceAccordingToStateActivity.this, PlaceDetailActivity.class);
                intent.putExtra("PHOTO_TRANSFER",mPlaceGridViewViewAdapter.getItem(position));
                startActivity(intent);
            }
        });

    }


    @Override
    public void onDataAvailable(List<Place> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status == DownloadStatus.OK) {
            mPlaceGridViewViewAdapter.loadNewData(data);
        } else {
            Log.e(TAG, "onDataAvailable failed with status " + status);
        }
        Log.d(TAG, "onDataAvailable: ends");
    }



    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.placeholder);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }

}
