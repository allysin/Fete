//package com.group9.fete.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.group9.fete.R;
//import com.group9.fete.model.Venue;
//import java.util.List;
//
///**
// * Created by Anubhav on 05-11-2014.
// */
//public class FeaturedVenueAdapter extends ArrayAdapter<Venue> {
//    private final Context context;
//    private final List<Venue> venueList;
//
//    public FeaturedVenueAdapter(Context c, int resourceId , List<Venue> items){
//        super(c, resourceId, items);
//        this.context = c;
//        this.venueList = items;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent){
//        //Create inflator
//        LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        //Get featured_venue_layout from inflator
//        View featuredVenues = inflator.inflate(R.layout.search_result_layout, parent, false);
//
//        //Fill images in imageView
//        ImageView imageView = (ImageView)featuredVenues.findViewById(R.id.venueImage);
//        String imagePath = venueList.get(position).GetVenueImage();
//        int resId = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());
//        imageView.setImageResource(resId);
//        String venueName = venueList.get(position).GetVenueName();
//        TextView venueNameTextView = (TextView)featuredVenues.findViewById(R.id.venueName);
//        venueNameTextView.setText(venueName);
//        return featuredVenues;
//    }
//}
