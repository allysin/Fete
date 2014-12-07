package com.group9.fete.model;

/**
 * Created by Anubhav on 07-12-2014.
 */
public class Amenities {
    private int id;

    private String name;

    private String icon;

    public Amenities(){}

    public Amenities(int id, String name, String icon){
        this.icon = icon;
        this.id = id;
        this.name = name;
    }

    public String GetName(){
        return name;
    }

    public String GetIcon(){
        return icon;
    }

    public int GetId(){return id;}
}
