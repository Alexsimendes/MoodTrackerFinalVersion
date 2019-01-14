package com.example.meleagant.moodtrackerfinalversion.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.meleagant.moodtrackerfinalversion.R;

public class MoodData {

    private static MoodData ourInstance = null;
    private Context context;
    private SharedPreferences mPreferences;
    public static int color[] = {R.color.faded_red, R.color.warm_grey, R.color.cornflower_blue_65, R.color.light_sage, R.color.banana_yellow};

    public static MoodData getInstance(Context appContext) {
        if (ourInstance == null) {
            ourInstance = new MoodData(appContext);
        }
        return ourInstance;
    }

    public static MoodData getInstance() throws Exception {
        if (ourInstance.context == null) {
            throw new Exception("No context defined");
        }
        return ourInstance;
    }

    //Moodtracker Context
    private MoodData(Context appContext) {
        this.context = appContext;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(appContext.getApplicationContext());

    }


}
