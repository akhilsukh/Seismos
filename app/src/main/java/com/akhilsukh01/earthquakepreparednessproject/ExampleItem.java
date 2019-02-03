package com.akhilsukh01.earthquakepreparednessproject;

public class ExampleItem {
    private String mTitle;
    private double mMag;
    private String mDate;

    public ExampleItem(String title, double mag ,String date){
        mTitle = title;
        mMag = mag;
        mDate = date;
    }

    public String getmZip(){
        return mTitle;
    }
    public double getmMag(){
        return mMag;
    }
    public String getmDate(){
        return mDate;
    }


}
