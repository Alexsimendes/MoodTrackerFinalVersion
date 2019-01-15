package com.example.meleagant.moodtrackerfinalversion.Controller;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.meleagant.moodtrackerfinalversion.Model.MoodData;
import com.example.meleagant.moodtrackerfinalversion.R;

import java.io.IOException;
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
    private MediaPlayer mMediaPlayer;
    private static final String RESOURCE = "android.resource://com.example.meleagant.moodtrackerfinalversion/";
    private int smiley[] = {R.drawable.smiley_sad, R.drawable.smiley_disappointed, R.drawable.smiley_normal, R.drawable.smiley_happy, R.drawable.smiley_super_happy};
    //copyright free music
    private int sound[] = {R.raw.sad, R.raw.disapointed, R.raw.normal, R.raw.happy, R.raw.super_happy};

    //Activity creation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mColor = findViewById(R.id.activity_main_layout);
        mSmiley = findViewById(R.id.activity_main_smiley_img);

        //get context, call Singleton
        try {
            MoodData.getInstance(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Create a new MediaPlayer
        mMediaPlayer = new MediaPlayer();

        //Launch methods
        this.moodDisplay();
        this.setImagesAndBackground();
        this.gestureDetector();
        this.commentBtn();
        this.historyBtn();
    }

    //Init background and smiley with currentmood
    public void setImagesAndBackground() {
        try {
            int color[] = MoodData.color;
            mColor.setBackgroundResource(color[mCurrentMood]);
            mSmiley.setImageResource(smiley[mCurrentMood]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Load last mood when activity start (or default mood if not used this day)
    public void moodDisplay() {
        try {
            mCurrentMood = MoodData.getInstance(this.getApplicationContext()).getCurrentMood().getMood();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Configure: button(comment)
    private void commentBtn() {
        Button mCommentBtn = findViewById(R.id.activity_main_comment_btn);
        mCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userComment();
            }

            //how to save mood and comment, toast creation
            private void userComment(){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Commentaire:");
                final EditText editText = new EditText(MainActivity.this);
                builder.setView(editText);

                //How to save user comment
                builder.setPositiveButton("VALIDER", new DialogInterface.OnClickListener() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mComment = editText.getText().toString();
                        try {
                            MoodData.getInstance().setCurrentMood(
                                    MoodData.getInstance().getCurrentMood().getDate(),
                                    MoodData.getInstance().getCurrentMood().getMood(),
                                    mComment);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //Save mood, comment, date
                        try {
                            MoodData.getInstance().saveData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //Toast
                        if (!mComment.equals("")) {
                            Toast.makeText(MainActivity.this, "Commentaire enregistré !", Toast.LENGTH_SHORT).show();
                        }

                    }

                }).setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.create();
                builder.show();
            }
        });
    }

    //Lunch History Activity
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


    //Detect swipe movement
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

    //Make vertical swipe only
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

    // If swipe up to down
    // Change Background, change Smiley, play Sound, update mood
    public void nextMood() {
        if (mCurrentMood < 4) {
            mCurrentMood++;

            try {
                int color[] = MoodData.color;
                mColor.setBackgroundResource(color[mCurrentMood]);
                mSmiley.setImageResource(smiley[mCurrentMood]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            playMusic(sound[mCurrentMood]);

            updateMood();
        }
    }

    // If swipe down to up
    // Change Background, change Smiley, play Sound, update mood
    public void previousMood() {
        if (mCurrentMood > 0) {
            mCurrentMood--;

            try {
                int color[] = MoodData.color;
                mColor.setBackgroundResource(color[mCurrentMood]);
                mSmiley.setImageResource(smiley[mCurrentMood]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            playMusic(sound[mCurrentMood]);

            updateMood();
        }
    }

    //Update mood in singleton and save it
    public void updateMood() {
        //Set mood
        try {
            MoodData.getInstance().setCurrentMood(mDate, mCurrentMood, mComment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Save mood, comment, date
        try {
            MoodData.getInstance().saveData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Media Player Config
    //Setting source for MediaPlayer without creating a new instance
    private void playMusic(int sound) {

        Uri path = Uri.parse(RESOURCE + sound);

        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(getApplicationContext(), path);
            mMediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.start();
    }

    //Set mDate in "onResume" to prevent some errors; load data
    @Override
    protected void onResume() {
        super.onResume();

        //Configure Calendar, get date
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

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onStop() {
        super.onStop();

        //Save mood, comment, date
        try {
            MoodData.getInstance().saveData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Stop and release MediaPlayer
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}