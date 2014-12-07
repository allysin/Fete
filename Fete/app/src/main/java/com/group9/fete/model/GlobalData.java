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
    private List<Review> appReviews;
    private List<Amenities> venueAmenities;

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
                    break;
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
                    break;
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

    public Amenities GetAmenity(int id){
        Amenities amenity = null;
        if (venueAmenities!=null) {
            for (Amenities am : venueAmenities) {
                if (am.GetId() == id) {
                    amenity = am;
                    break;
                }
            }
        }
        return amenity;
    }

    private void SetAmenities(JSONArray amenities){
        this.venueAmenities = new ArrayList<Amenities>();
        try {
            for (int i = 0; i < amenities.length(); i++) {
                JSONObject amenity = amenities.getJSONObject(i);
                int amenityId = amenity.getInt("amenityID");
                String amenityName = amenity.getString("amenityName");
                String amenityIcon = amenity.getString("amenityIcon");
                Amenities amenityObj = new Amenities(amenityId, amenityName, amenityIcon);
                this.venueAmenities.add(amenityObj);
             }
        }catch (Exception e){
            Log.e(Tag, e.getMessage());
        }
    }

    public void SetData(){
        String jsonString = getJsonData();

        try {
        if (appVenues == null || appUsers == null){
            appVenues = new ArrayList<Venue>();
            appUsers = new ArrayList<User>();
            appReviews = new ArrayList<Review>();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray venueArray = jsonObject.getJSONArray("venues");
            JSONArray userArray = jsonObject.getJSONArray("users");
            JSONArray reviewsArray = jsonObject.getJSONArray("reviews");
            JSONArray amenitiesArray = jsonObject.getJSONArray("amenities");
            SetAmenities(amenitiesArray);

            for (int i=0; i<reviewsArray.length(); i++){
                JSONObject review = reviewsArray.getJSONObject(i);
                int reviewID = review.getInt("reviewID");
                int userID = review.getInt("userID");
                int venueID = review.getInt("venueID");
                String message = review.getString("reviewText");
                Review r = new Review(reviewID, userID, venueID, message);
                appReviews.add(r);
            }

            for(int i=0; i<venueArray.length(); i++){
                JSONObject venue = venueArray.getJSONObject(i);
                int ownerID = venue.getInt("ownerID");
                int venueID = venue.getInt("venueID");
                String venueImage = venue.getString("venueImage");
                String venueName = venue.getString("venueName");
                String venueDescription = venue.getString("venueDescription");
                JSONArray amenityList = venue.getJSONArray("venueAmenities");
                ArrayList<Amenities> amenities = new ArrayList<Amenities>();
                for(int j=0;j<amenityList.length();j++){
                    JSONObject amenity = amenityList.getJSONObject(j);
                    int amenityId = amenity.getInt("amenityID");
                    Amenities tempAmenity = venueAmenities.get(amenityId);
                    amenities.add(tempAmenity);
                }
                ArrayList<Review> venueReviews = new ArrayList<Review>();
                for(int j=0;j<appReviews.size();j++){
                    Review tempReview = appReviews.get(j);
                    if (tempReview.GetVenueID()==venueID){
                        venueReviews.add(tempReview);
                        break;
                    }
                }
                Venue v = new Venue(venueID, venueName, venueDescription, venueImage, ownerID, venueReviews, amenities);
                appVenues.add(v);
            }

            for(int i=0; i<userArray.length(); i++){
                JSONObject user = userArray.getJSONObject(i);
                int userID = user.getInt("userID");
                String userName = user.getString("username");
                String userImage = user.getString("userimage");
                String userBio = user.getString("bio");
                JSONArray ownedVenues = user.getJSONArray("ownedVenues");
                ArrayList<Integer> venueList = new ArrayList<Integer>();
                for (int j=0; j<ownedVenues.length(); j++) {
                    JSONObject venue = ownedVenues.getJSONObject(j);
                    venueList.add(venue.getInt("venueID"));
                }

                User u = new User(userID, userName, userImage,userBio,venueList);
                appUsers.add(u);

                Log.v(Tag, jsonObject.toString());
            }
        }
        }catch(Exception e)
        {
            Log.e(Tag, e.getMessage());
        }
    }

    @Override
    public void onCreate(){
        super.onCreate();
        //Application will initialize global data only once during application start
        SetData();
    }
}
