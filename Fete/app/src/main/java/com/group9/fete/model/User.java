package com.group9.fete.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anubhav on 02-11-2014.
 */
public class User {
    /*all the attributes defined about the venue json object has corresponding variables in model class
    so that they can be mapped easily. so each venueID in json goes into id variable here and the same thing happens to other variables.
    Also notice that all the variables are private and have corresponding get set methods that are public.*/
    private int userID;

    private String userName;

    private String userImage;

    private String bio;

    private ArrayList<Integer> userVenues;

    private ArrayList<Integer> favoriteVenues;

    public User(){
        userID = 0;
        userName = new String();
        userImage = new String();
        bio = new String();
        userVenues = new ArrayList<Integer>();
    }

    /*There are 2 constructors in our class. To call the one above, use Venue v = new Venue();, in that case v.id = 0, v.venueName = ""
    and everything else is empty.
    To use the one below, use Venue v = new Venue(2, "Backyard with Pool", "Amazing backyard with pool, lighting and barbeque.", 1);
    In this case v.id will be 2, v.venueName will be "Backyard with Pool" and so on.*/
    public User(int id, String userName, String userImage, String bio, ArrayList<Integer> venues){
        this.userID = id;
        this.userName = userName;
        this.userImage = userImage;
        this.bio = bio;
        this.userVenues = venues;
    }

    /*Get set methods help you control access to your variables. GetUserID does not have a corresponding SetUserID function
    because we never want the user to change the id of a user. So how do I put value in the id variable for the first time if
    there is no SetUserID?? We use a public constructor that fills ID from the json file.*/
    public int GetUserID(){
        return userID;
    }

    public String GetUserName(){
        return userName;
    }

    /*The parameter newName will come from the user when he types the name of his place on the VenueDetail page*/
    public void SetUserName(String newName){
        userName = newName;
    }

    public String GetUserImage(){
        return userImage;
    }

    public String GetUserBio(){return bio;}

    public void SetUserBio(String newBio){bio = newBio;}

    public void SetUserImage(String userImage){
        /*this refers to the object using the function. So when we do User u = new User();
        and u.SetUserImage("userImage0"), the parameter userImage above gets filled with "userImage0",
        and the line below becomes u.userImage = "userImage0";*/
        this.userImage = userImage;
    }

    public List<Integer> GetUserVenues(){
        return userVenues;
    }

    public List<Integer> GetFavoriteVenues(){
        return favoriteVenues;
    }
}
