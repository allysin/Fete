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

    public Venue GetVenue(int id) {
        Venue filteredVenue = null;
        if (appVenues == null) {
            filteredVenue = null;
        }
        else {
            for (Venue v : appVenues) {
                int venueId = v.GetID();
                if (venueId == id) {
                    filteredVenue = v;
                }
            }
        }

        return filteredVenue;
    }

    public User GetUser(int id){
        User user = null;
        if (appUsers == null){
            user = null;
        }
        else {
            for (User u:appUsers){
                int userId = u.GetUserID();
                if (userId == id) {
                    user = u;
                }
            }
        }

        return user;
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

        if (appUsers == null){
            appUsers = new ArrayList<User>();
        }

        String jsonString = getJsonData();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray venueArray = jsonObject.getJSONArray("venues");
            JSONArray userArray = jsonObject.getJSONArray("users");
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

            for(int i=0; i<userArray.length(); i++){
                JSONObject user = userArray.getJSONObject(i);
                int userID = user.getInt("userID");
                String userName = user.getString("username");
                String userImage = user.getString("userimage");
                JSONArray ownedVenues = user.getJSONArray("ownedVenues");
                ArrayList<Integer> venueList = new ArrayList<Integer>();
                for (int j=0; j<ownedVenues.length(); j++) {
                    JSONObject venue = ownedVenues.getJSONObject(j);
                    venueList.add(venue.getInt("venueID"));
                }
                User u = new User(userID, userName, userImage, venueList);
                appUsers.add(u);

                Log.v(Tag, jsonObject.toString());
            }
        }catch(Exception e)
        {
            Log.e(Tag, e.getMessage());
        }
    }
}
