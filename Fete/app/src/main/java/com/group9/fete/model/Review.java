package com.group9.fete.model;

/**
 * Created by Allyson on 11/10/14.
 */
public class Review {

    private int reviewID;

    private int userID;

    private int venueID;

    private String reviewText;

    public Review(){
        reviewID = 0;
        userID = 0;
        venueID = 0;
        reviewText = new String();
    }


    public Review(int reviewID, int userID, int venueID, String reviewText){
        this.userID = userID;
        this.reviewID = reviewID;
        this.venueID = venueID;
        this.reviewText = reviewText;
    }

    public int GetReviewID(){
        return reviewID;
    }

    public String GetReviewText(){
        return reviewText;
    }

    public int GetUserID(){
        return userID;
    }

    public int GetVenueID(){
        return venueID;
    }


}
