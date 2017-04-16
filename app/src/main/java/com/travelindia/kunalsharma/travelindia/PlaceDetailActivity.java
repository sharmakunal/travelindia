package com.travelindia.kunalsharma.travelindia;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class PlaceDetailActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
       // activateToolbar(true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       // collapsingToolbarLayout.setTitle(getResources().getString(R.string.user_name));

        dynamicToolbarColor();


        Intent intent = getIntent();
        Place place = (Place) intent.getSerializableExtra(PHOTO_TRANSFER);
        if(place != null) {



            TextView photoTitle = (TextView) findViewById(R.id.place_title);

            String title = place.getPname();
            photoTitle.setText(title);


            TextView photoInfo = (TextView) findViewById(R.id.place_info);

            String info = place.getPinfo();
            photoInfo.setText(info);

            TextView photoCity = (TextView) findViewById(R.id.place_city);

            String city = place.getPcity();
            photoCity.setText(city);

            TextView photoState = (TextView) findViewById(R.id.place_state);


            String state = place.getPState();
            photoState.setText(state);

            TextView photoCountry = (TextView) findViewById(R.id.place_country);


            String country = place.getPCountry();
            photoCountry.setText(country);

            TextView photoNearby = (TextView) findViewById(R.id.place_nearby);


            String nearby = place.getPcity();
            photoNearby.setText(nearby);

            ImageView photoImage = (ImageView) findViewById(R.id.placethumbnail);
            Picasso.with(this).load(place.getPthumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(photoImage);
        }



    }


private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.profile_pic);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }
}