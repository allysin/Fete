package com.group9.fete;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.group9.fete.model.GlobalData;
import com.group9.fete.model.Venue;

import java.util.List;

/**
 * Created by Anubhav on 05-11-2014.
 */
public class HomeFragment extends Fragment {
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context cont = getActivity();
        GlobalData data = ((HomePage)getActivity()).AppData;
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        ImageView userImage = (ImageView)rootView.findViewById(R.id.userImage);





        userImage.setImageResource(R.drawable.nina);
//            TextView userDetailView = (TextView)rootView.findViewById(R.id.userDetail);
//            userDetailView.setText("Nina Dobrev is our featured user this week. She likes to knit and ride bicycles" +
//                    "down by the river whenever...");

        //code will fetch images named poolimage with numbers concatenated
//        String filenameVenueImage = getString(R.string.venue_image_names);
//        String fileNumberTotalString = getString(R.string.number_of_venues);
//        Integer totalFiles = Integer.parseInt(fileNumberTotalString);

        List<Venue> featuredVenues = data.GetVenues();
        Integer totalRows = 0;
        //Should come from the global data item TODO
        int itemsInRow = 4;
        if (featuredVenues.size()%itemsInRow>0){
            totalRows = (featuredVenues.size()/itemsInRow) + 1;
        }else {
            totalRows = featuredVenues.size() /itemsInRow;
        }
        TableLayout venueTable = (TableLayout)rootView.findViewById(R.id.venueTable);
        for (int i=0;i<totalRows;i++ )
        {
            //create tablerows dynamically, add imageviews to it filled with appropiate images
            TableRow row = new TableRow(cont);
            int rowLimit = featuredVenues.size()-(i*itemsInRow);
            if (rowLimit>itemsInRow){
                rowLimit = itemsInRow;
            }
            for (int j=0;j<rowLimit;j++) {
                View featuredVenue = inflater.inflate(R.layout.featured_venue_layout, null, false);
                View customImageView = featuredVenue.findViewById(R.id.venueImage);
                Venue v = featuredVenues.get(i*itemsInRow + j);
                int resID = getResources().getIdentifier(v.GetVenueImage(), "drawable", cont.getPackageName());
                ((ImageView) customImageView).setImageResource(resID);
                //customImageView.layout(10, 10, 10, 10);
                //once the row is filled, all row to the table
                row.addView(featuredVenue);
            }
            venueTable.addView(row);
        }
        return rootView;
    }
}
