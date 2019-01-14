package com.example.meleagant.moodtrackerfinalversion.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.meleagant.moodtrackerfinalversion.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

public class MoodData {
    @SuppressLint("StaticFieldLeak")
    private static MoodData ourInstance = null;
    private int mCal = currentDate();
    private int mDate = currentDate();
    private LinkedHashMap <Object, Object> moodMap;
    private SharedPreferences mPreferences;
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String MOOD_DATA = "MOOD_DATA";
    private Context context;
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
        loadData();
    }

    //Method to save data
    public void saveData() {
        mPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(moodMap);
        editor.putString(MOOD_DATA, json);
        editor.apply();
    }

    //Method to load data
    public void loadData() {
        currentDate();
        Gson gson = new Gson();
        mPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String json = mPreferences.getString(MOOD_DATA, null);

        if (json == null){
            moodMap = new LinkedHashMap<>();
        }
        else {
            Type mapType = new TypeToken<LinkedHashMap<Integer, MoodList>>() {}.getType();
            moodMap = gson.fromJson(json, mapType);
            getCurrentMood();
        }

        for (int day = 0; day < 8; day++){
            int key = currentDate(-day);
            if (!moodMap.containsKey(key)){
                moodMap.put(key, new MoodList(key, 3, ""));
            }
            if (moodMap.size() > 8 ){
                moodMap.remove(currentDate(-8));
            }
        }
    }

    //Get current date
    private int currentDate() {
        return  currentDate(0);
    }

    //Get a calendar
    private int currentDate(int days) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.DATE, days);
        return (calendar.get(Calendar.YEAR) * 10000) +
                ((calendar.get(Calendar.MONTH)+1) * 100) +
                (calendar.get(Calendar.DAY_OF_MONTH));
    }

    //Get mood history using an index
    public MoodList getMoodByIndex(int index) {
        return getMood(currentDate(-index));
    }

    //Able MoodList gestion with "int Date"
    private MoodList getMood(int date){
        //If the app was used during the current day, update with current values
        MoodList current;

        if (moodMap.containsKey(date)){
            current = (MoodList) moodMap.get(date);
        }
        //If the app wasn't used, put default MoodList
        else {
            current = new MoodList(date,3, "");
            moodMap.put(date, current);
        }
        return current;
    }


    //Get current mood using "getMood" but with mDate argument
    public MoodList getCurrentMood(){
        return getMood(mDate);
    }

    //How to set mood
    public void setCurrentMood(int mDate, int mCurrentMood, String mComment){

        currentDate();

        //If current date doesn't exist in moodMap: create default mood
        if (!moodMap.containsKey(mCal)){
            moodMap.put(mCal, new MoodList(mDate, 3, ""));
        }

        //If current date exist in moodMap: update mood
        else{
            MoodList current = (MoodList) moodMap.get(mCal);
            current.setDate(mDate);
            current.setMood(mCurrentMood);
            current.setComment(mComment);
        }
    }
}