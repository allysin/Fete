package com.group9.fete.model;

import android.app.Application;
import android.util.Log;

import com.group9.fete.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anubhav on 02-11-2014.
 */
public class GlobalData extends Application {
    private List<Venue> appVenues;
    private List<User> appUsers;

    private String getJsonData(){
        String text = new String();
        try {
            InputStream is = getResources().openRawResource(R.raw.fete);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);


        }catch(Exception e){
            Log.e("Error",e.getMessage());
        }
        return text;
    }

    public List<Venue> GetVenues(){
        if (appVenues==null){
            appVenues = new ArrayList<Venue>();
        }
        return appVenues;
    }

    public List<User> GetUsers(){
        if (appUsers==null){
            appUsers = new ArrayList<User>();
        }
        return appUsers;
    }

    public void SetData(){
        String json = getJsonData();
    }
}
