package com.example.meleagant.moodtrackerfinalversion.Controller;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.meleagant.moodtrackerfinalversion.Model.MoodData;
import com.example.meleagant.moodtrackerfinalversion.Model.MoodList;
import com.example.meleagant.moodtrackerfinalversion.R;


public class HistoryActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayoutDaysAgo[] = new RelativeLayout[7];
    private ImageView mImageViewDaysAgo[] = new ImageView[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //get context, call Singleton
        try {
            MoodData.getInstance(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.historyDisplay();
    }

    //Configure RL and IV
    private void historyDisplay() {
        mRelativeLayoutDaysAgo[0] = findViewById(R.id.activity_history_layout_seven_days_ago);
        mRelativeLayoutDaysAgo[1] = findViewById(R.id.activity_history_layout_six_days_ago);
        mRelativeLayoutDaysAgo[2] = findViewById(R.id.activity_history_layout_five_days_ago);
        mRelativeLayoutDaysAgo[3] = findViewById(R.id.activity_history_layout_four_days_ago);
        mRelativeLayoutDaysAgo[4] = findViewById(R.id.activity_history_layout_three_days_ago);
        mRelativeLayoutDaysAgo[5] = findViewById(R.id.activity_history_layout_two_days_ago);
        mRelativeLayoutDaysAgo[6] = findViewById(R.id.activity_history_layout_yesterday);

        //Make all Relative Layouts clickable
        for (int i = 0; i < 7; i++){
            mRelativeLayoutDaysAgo[i].setOnClickListener(onClickLayout);
        }

        mImageViewDaysAgo[0] = findViewById(R.id.activity_history_img_seven_days_ago);
        mImageViewDaysAgo[1] = findViewById(R.id.activity_history_img_six_days_ago);
        mImageViewDaysAgo[2] = findViewById(R.id.activity_history_img_five_days_ago);
        mImageViewDaysAgo[3] = findViewById(R.id.activity_history_img_four_days_ago);
        mImageViewDaysAgo[4] = findViewById(R.id.activity_history_img_three_days_ago);
        mImageViewDaysAgo[5] = findViewById(R.id.activity_history_img_two_days_ago);
        mImageViewDaysAgo[6] = findViewById(R.id.activity_history_img_Yesterday);

        //Update Relative Layouts with mood index
        for(int i = 0; i < 7; i++){
            try {
                MoodList mMood = MoodData.getInstance().getMoodByIndex(7-i);
                layoutDisplay(mRelativeLayoutDaysAgo[i], mMood.getMood());
                //Config imageview visibility
                String mComment = mMood.getComment();
                if (!mComment.equals("")){
                    mImageViewDaysAgo[i].setVisibility(View.VISIBLE); }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Get user phone screen width
    private int widthPhone() {
        DisplayMetrics mPhoneScreen = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhoneScreen);
        return mPhoneScreen.widthPixels;
    }

    //Update Layout with color and width
    private void layoutDisplay(RelativeLayout relativeLayout, int mCurrentMood) {
        ViewGroup.LayoutParams params = relativeLayout.getLayoutParams();
        int mWidthScreen = widthPhone();

        //Configure Relative Layout width and color by mood
        try {
            int color[] = MoodData.color;
            params.width =  (mCurrentMood+1)*mWidthScreen/5;
            relativeLayout.setBackground(getResources().getDrawable(color[mCurrentMood]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Config toast
    private final View.OnClickListener onClickLayout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Configure Toast, when you click on ImageView
            try {
                //XML Tags are use to connect comments/imageviews with the rigth Relative Layouts
                MoodList mMood = MoodData.getInstance().getMoodByIndex(Integer.parseInt(v.getTag().toString()));
                Toast.makeText(HistoryActivity.this, mMood.getComment(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //loadData method call is in "onResume" to make update more efficient
    @Override
    protected void onResume() {
        super.onResume();

        try {
            MoodData.getInstance().loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}