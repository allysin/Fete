package com.group9.fete.model;

import java.util.ArrayList;

/**
 * Created by Anubhav on 02-11-2014.
 */
public class Venue {
    /*all the attributes defined about the venue json object has corresponding variables in model class
    so that they can be mapped easily. so each venueID in json goes into id variable here and the same thing happens to other variables.
    Also notice that all the variables are private and have corresponding get set methods that are public.*/
    private int id;

    private String venueName;

    private String venueDescription;

    private String venueImage;

    private int ownerID;

    private ArrayList<Review> reviews;

    public Venue(){
        id = 0;
        venueName = new String();
        venueDescription = new String();
        ownerID = 0;
        reviews = new ArrayList<Review>();
    }

    /*There are 2 constructors in our class. To call the one above, use Venue v = new Venue();, in that case v.id = 0, v.venueName = ""
    and everything else is empty.
    To use the one below, use Venue v = new Venue(2, "Backyard with Pool", "Amazing backyard with pool, lighting and barbeque.", 1);
    In this case v.id will be 2, v.venueName will be "Backyard with Pool" and so on.*/
    public Venue(int id, String venueName, String venueDescription, String venueImage, int ownerID, ArrayList<Review> reviews){
        this.id = id;
        this.venueName = venueName;
        this.venueDescription = venueDescription;
        this.ownerID = ownerID;
        this.venueImage = venueImage;
        this.reviews = reviews;
    }

    /*Get set methods help you control access to your variables. GetID does not have a corresponding SetID function
    because we never want the user to change the id of a place. So how do I put value in the id variable for the first time if
    there is no SetID?? We use a public constructor that fills ID from the json file.*/
    public int GetID(){
        return id;
    }

    public String GetVenueName(){
        return venueName;
    }

    /*The parameter newName will come from the user when he types the name of his place on the VenueDetail page*/
    public void SetVenueName(String newName){
        venueName = newName;
    }

    public String GetVenueDescription(){
        return venueDescription;
    }

    public void SetVenueDescription(String venueDescription){
        /*this refers to the object using the function. So when we do Venue v = new Venue();
        and v.SetVenueDescription("hello"), the parameter venueDescription above gets filled with "hello",
        and the line below becomes v.venueDescription = "hello";*/
        this.venueDescription = venueDescription;
    }

    public String GetVenueImage(){return this.venueImage;}

    public void SetVenueImage(String venueImage){
        this.venueImage = venueImage;
    }

    public int GetOwnerID(){
        return ownerID;
    }
}
