package com.travelindia.kunalsharma.travelindia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.travelindia.kunalsharma.travelindia.Fragments.About_Us;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,GetCategoryJson.OnDataAvailable,
        RecyclerItemClickListener.OnRecyclerClickListener{


     private static final String TAG = "Main Activity";
     private CategoryRecylerViewAdapter mcategoryRecyclerViewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        RecyclerView recyclerView;

        GetCategoryJson gettraveljsondata = new GetCategoryJson(this);
        gettraveljsondata.execute();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, this));

        mcategoryRecyclerViewAdapter = new CategoryRecylerViewAdapter(this, new ArrayList<Category>());
        recyclerView.setAdapter(mcategoryRecyclerViewAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_about_us:
                /*About_Us cameraImportFragment = new About_Us();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main,cameraImportFragment)
                        .addToBackStack(null)
                        .commit();*/
               // fragment = new About_Us();
                break;
        }

        //replacing the fragment
        /*if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.replace(R.id.content_main, fragment);
            ft.commit();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        displaySelectedScreen(item.getItemId());
        return true;
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
        //Toast.makeText(this, "Normal tap at position " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PlaceListActivity.class);
        intent.putExtra("PHOTO_TRANSFER",mcategoryRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);
    }
}
