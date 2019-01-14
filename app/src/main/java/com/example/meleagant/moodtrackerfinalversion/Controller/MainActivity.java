package com.example.meleagant.moodtrackerfinalversion.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.meleagant.moodtrackerfinalversion.R;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mColor;
    private ImageView mSmiley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColor = findViewById(R.id.activity_main_layout);
        mSmiley = findViewById(R.id.activity_main_smiley_img);

    }
}
