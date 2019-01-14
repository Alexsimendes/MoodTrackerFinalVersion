package com.example.meleagant.moodtrackerfinalversion.Model;



public class MoodList {
    private int mMood;
    private String mComment;
    private int mDate;

    MoodList(int date, int mood, String comment) {
        mDate = date;
        mMood = mood;
        mComment = comment;
    }

    public int getDate() {
        return this.mDate;
    }

    public void setDate(int date) {
        mDate = date;
    }

    public int getMood() {
        return this.mMood;
    }

    void setMood(int mood) {
        mMood = mood;
    }

    public String getComment() {
        return this.mComment;
    }

    void setComment(String comment) {
        mComment = comment;
    }
}