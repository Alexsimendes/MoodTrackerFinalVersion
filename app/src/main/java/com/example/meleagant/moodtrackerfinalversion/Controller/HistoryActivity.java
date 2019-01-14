package com.example.meleagant.moodtrackerfinalversion.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.meleagant.moodtrackerfinalversion.R;

public class HistoryActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayoutDaysAgo[] = new RelativeLayout[7];
    private ImageView mImageViewDaysAgo[] = new ImageView[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

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
        for (int i = 0; i < 7; i++) {
            mRelativeLayoutDaysAgo[i].setOnClickListener(onClickLayout);
        }

        mImageViewDaysAgo[0] = findViewById(R.id.activity_history_img_seven_days_ago);
        mImageViewDaysAgo[1] = findViewById(R.id.activity_history_img_six_days_ago);
        mImageViewDaysAgo[2] = findViewById(R.id.activity_history_img_five_days_ago);
        mImageViewDaysAgo[3] = findViewById(R.id.activity_history_img_four_days_ago);
        mImageViewDaysAgo[4] = findViewById(R.id.activity_history_img_three_days_ago);
        mImageViewDaysAgo[5] = findViewById(R.id.activity_history_img_two_days_ago);
        mImageViewDaysAgo[6] = findViewById(R.id.activity_history_img_Yesterday);

    }


    private final View.OnClickListener onClickLayout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
