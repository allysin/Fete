package com.group9.fete;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group9.fete.model.GlobalData;
import com.group9.fete.model.User;
import com.group9.fete.model.Venue;

import java.util.List;

/**
 * Created by Anubhav on 05-11-2014.
 */
public class HomeFragment extends Fragment {
    private int featuredUserId = 2;

    ///welcome screen
    final String welcomeScreenShownPref = "welcomeScreenShown";
    SharedPreferences mPrefs;

    public final static String EXTRA_MESSAGE = "com.group9.fete.HomePage.MESSAGE";

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context cont = getActivity();
        GlobalData data = (GlobalData)(getActivity().getApplication());
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        ImageView userImage = (ImageView)rootView.findViewById(R.id.userImageHome);
        userImage.setImageResource(R.drawable.nina);
//            TextView userDetailView = (TextView)rootView.findViewById(R.id.userDetail);
//            userDetailView.setText("Nina Dobrev is our featured user this week. She likes to knit and ride bicycles" +
//                    "down by the river whenever...");

        //code will fetch images named poolimage with numbers concatenated
//        String filenameVenueImage = getString(R.string.venue_image_names);
//        String fileNumberTotalString = getString(R.string.number_of_venues);
//        Integer totalFiles = Integer.parseInt(fileNumberTotalString);
        WindowManager wm = (WindowManager) cont.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        float ratio = width/(float)height;
        List<Venue> featuredVenues = data.GetVenues();
        Integer totalRows = 0;
        //Should come from the global data item TODO
        int itemsInRow = 2;
        if (featuredVenues.size()%itemsInRow>0){
            totalRows = (featuredVenues.size()/itemsInRow) + 1;
        }else {
            totalRows = featuredVenues.size() /itemsInRow;
        }

        LinearLayout venueTable = (LinearLayout)rootView.findViewById(R.id.venueTable);
        for (int i=0;i<totalRows;i++ )
        {
            //create tablerows dynamically, add imageviews to it filled with appropiate images
            int rowLimit = featuredVenues.size()-(i*itemsInRow);
            if (rowLimit>itemsInRow){
                rowLimit = itemsInRow;
            }
            LinearLayout rowLayout  = new LinearLayout(cont);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowLayout.setWeightSum(10);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setLayoutParams(lp);
            int imageSize = 0;
            if (ratio>1){
                imageSize = (width/3);
            }
            else{
                imageSize = height/4;
            }
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, imageSize);

            //rowLayout.setWeightSum(1f);
            //rowLayout.setLayoutParams(param);
            param.weight = 5f;
            param.gravity = Gravity.CENTER;
            for (int j=0;j<rowLimit;j++) {
                //View featuredVenue = inflater.inflate(R.layout.featured_venue_layout, null, false);
                //ImageView customImageView = (ImageView)featuredVenue.findViewById(R.id.venueImage);
                ImageView customImageView = new ImageView(cont);
                Venue v = featuredVenues.get(i*itemsInRow + j);
                final int venueId = v.GetID();
                int resID = getResources().getIdentifier(v.GetVenueImage(), "drawable", cont.getPackageName());
                customImageView.setImageResource(resID);
                customImageView.setPadding(10,10,10,10);
                customImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                //customImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                customImageView.setLayoutParams(param);
                customImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToProperty(view, cont, venueId);
                    }
                });

//                customImageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent detailIntent = new Intent(cont, VenueDetail.class);
//                        detailIntent.putExtra(getString(R.string.venueId), venueId);
//                        startActivity(detailIntent);
//            }
//                });
                rowLayout.addView(customImageView, j);
            }

            //once the row is filled, all row to the table
            venueTable.addView(rowLayout);
        }

        View featuredUserTile = rootView.findViewById(R.id.featuredUserTileHome);
        User featured = data.GetUser(featuredUserId);
        TextView nameTxtView = (TextView)featuredUserTile.findViewById(R.id.userNameHome);
        nameTxtView.setText(featured.GetUserName());
        List<Integer> venueIds = featured.GetUserVenues();
        Venue featuredVenue = data.GetVenue(venueIds.get(0));
        ImageView featuredVenueImage = (ImageView)rootView.findViewById(R.id.backgroundFeaturedUserImage);
        int resID = getResources().getIdentifier(featuredVenue.GetVenueImage(), "drawable", cont.getPackageName());
        featuredVenueImage.setImageResource(resID);
        TextView detailText = (TextView)featuredUserTile.findViewById(R.id.userDetailHome);
        String limitedText = featured.GetUserBio().substring(0,90);
        detailText.setText(limitedText + "...");
        featuredUserTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUser(view, cont, featuredUserId);
            }
        });
        return rootView;
    }

    public void goToUser(View view, Context cont, int uniqueIdentifier){
        Intent userIntent = new Intent(cont, UserDetail.class);
        userIntent.putExtra(getString(R.string.userIdParam), uniqueIdentifier);
        startActivity(userIntent);
    }

    public void goToProperty(View view, Context cont,int uniqueIdentifier){
        Intent detailIntent = new Intent(cont, VenueDetail.class);
        detailIntent.putExtra(getString(R.string.venueIdParam), uniqueIdentifier);
        startActivity(detailIntent);
    }

//    public void goToPropertyLeft(View view){
//        Intent userIntent = new Intent(this, VenueDetail.class);
//        int venueId = 5;
//        userIntent.putExtra(getString(R.string.venueId), venueId);
//        startActivity(userIntent);
//    }
}
