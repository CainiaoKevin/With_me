package com.example.kefeng.withyou;


import android.location.LocationManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by kefeng on 7/15/2017.
 */

public class MapUser {
    private  String userName;
    private  String email;
    private  double latitude;
    private  double longitude;
    private  String currentTime;

    public MapUser(String userName, String email, double latitude, double longitude, String currentTime) {
        this.userName = userName;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currentTime = currentTime;
    }







    public MapUser() {
        this.userName = null;
        this.email = null;
        this.latitude = 0;
        this.longitude = 0;
        this.currentTime = null;
    }

   /* public String getUserName() {
        return userName;
    }


    public String getEmail() {
        return email;
    }

    public String getCurrentTime() {

        return currentTime;
    }

    public void setCurrentTime(){
        this.currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }*/

    public String getUserName() {return userName;}

    public String getEmail() {
        return email;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCurrentTime() {
        return currentTime;
    }
}
