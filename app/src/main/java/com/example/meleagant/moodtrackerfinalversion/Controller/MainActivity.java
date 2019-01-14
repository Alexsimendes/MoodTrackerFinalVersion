package com.example.meleagant.moodtrackerfinalversion.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.meleagant.moodtrackerfinalversion.R;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mColor;
    private ImageView mSmiley;
    public static final int BUNDLE_REQUEST_CODE = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColor = findViewById(R.id.activity_main_layout);
        mSmiley = findViewById(R.id.activity_main_smiley_img);

        this.historyBtn();
        this.commentBtn();

    }


    // Wire widget, configure: button(History)
    // How to lunch History Activity
    private void historyBtn() {
        Button mHistory = findViewById(R.id.activity_main_history_btn);
        mHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
                startActivityForResult(historyActivity, BUNDLE_REQUEST_CODE);
            }
        });
    }

    // Wire widget, configure: button(comment)
    // How to comment
    private void commentBtn() {
        Button mCommentBtn = findViewById(R.id.activity_main_comment_btn);
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userComment();
            }

            //how to save mood and comment, toast creation
            private void userComment() {
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onStop() {
        super.onStop();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
