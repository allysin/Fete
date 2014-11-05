package com.group9.fete.model;

import android.app.Application;
import android.util.Log;

import com.group9.fete.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anubhav on 02-11-2014.
 */
public class GlobalData extends Application {
    private String Tag = "GlobalData";
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
        if (appVenues == null){
            appVenues = new ArrayList<Venue>();
        }
        String jsonString = getJsonData();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray venueArray = jsonObject.getJSONArray("venues");
            for(int i=0; i<venueArray.length(); i++){
                JSONObject venue = venueArray.getJSONObject(i);
                int ownerID = venue.getInt("ownerID");
                int venueID = venue.getInt("venueID");
                String venueImage = venue.getString("venueImage");
                String venueName = venue.getString("venueName");
                String venueDescription = venue.getString("venueDescription");
                Venue v = new Venue(venueID, venueName, venueDescription, venueImage, ownerID);
                appVenues.add(v);
            }
            Log.v(Tag, jsonObject.toString());
        }catch(Exception e)
        {
            Log.e(Tag, e.getMessage());
        }
    }
}
