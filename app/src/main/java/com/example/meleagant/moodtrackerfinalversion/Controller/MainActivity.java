package com.example.meleagant.moodtrackerfinalversion.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.meleagant.moodtrackerfinalversion.Model.MoodData;
import com.example.meleagant.moodtrackerfinalversion.R;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private RelativeLayout mColor;
    private ImageView mSmiley;
    private String mComment;
    private int mCurrentMood = 8;
    private int mDate;
    public static final int BUNDLE_REQUEST_CODE = 77;
    private GestureDetectorCompat mGesture;
    private int smiley[] = {R.drawable.smiley_sad, R.drawable.smiley_disappointed, R.drawable.smiley_normal, R.drawable.smiley_happy, R.drawable.smiley_super_happy};
    //copyright free music
    private int sound[] = {R.raw.sad, R.raw.disapointed, R.raw.normal, R.raw.happy, R.raw.super_happy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColor = findViewById(R.id.activity_main_layout);
        mSmiley = findViewById(R.id.activity_main_smiley_img);

        //get context by calling Singleton
        try {
            MoodData.getInstance(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.gestureDetector();
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

    //Configuration: Gesture Detector
    //How to detect swipe movement
    @SuppressLint("ClickableViewAccessibility")
    private void gestureDetector() {
        mGesture = new GestureDetectorCompat(this, this);
        mColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGesture.onTouchEvent(event);
                return true;
            }
        });
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    //How to make vertical swipe only
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //Vertical axe
        float Y = Math.abs(e1.getY() - e2.getY());
        //Horizontal axe
        float X = Math.abs(e1.getX() - e2.getX());
        //Initialisation vertical scroll
        if (Y > X) {
            if (e1.getY() - e2.getY() < 0) {
                this.previousMood();
            }
            else {
                this.nextMood();
            }
        }
        return false;

    }


    // If swipe up to down (configuration)
    // Change Background, change Smiley, play Sound
    public void nextMood() {

    }

    // If swipe down to up (configuration)
    // Change Background, change Smiley, play Sound
    public void previousMood() {

    }


    @Override
    protected void onResume() {
        super.onResume();

        //Configure Calendar
        Calendar mCalendar = Calendar.getInstance(TimeZone.getDefault());
        mDate = (mCalendar.get(Calendar.YEAR)*10000)+
                (mCalendar.get(Calendar.MONTH)*100)+
                (mCalendar.get(Calendar.DAY_OF_MONTH));

        //Load Data
        try {
            MoodData.getInstance().loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();

        //Save in Shared Preferences and Gson/Json
        try {
            MoodData.getInstance().saveData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
