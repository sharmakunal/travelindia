package com.travelindia.kunalsharma.travelindia.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.travelindia.kunalsharma.travelindia.PojoClasses.State;
import com.travelindia.kunalsharma.travelindia.R;

public class PlaceAccordingToStateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_according_to_state);

        Intent intent = getIntent();
        State state = (State) intent.getSerializableExtra("Place");
        if(state != null) {

            TextView stateTitle = (TextView) findViewById(R.id.textView2);

            String title = state.getStateName();
            stateTitle.setText(title);

        }
        else{
            TextView stateTitle = (TextView) findViewById(R.id.textView2);

            String title = "Nothing";
            stateTitle.setText(title);
        }

    }
}
