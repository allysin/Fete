package com.group9.fete;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }
    ImageView image;

//    // a list class type must be used when using a list view
//    // list items are added to a list view programatically and not through xml
//    List<Map<String, String>> teamsList = new ArrayList<Map<String,String>>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_overview);
//
//        // we need to pick which view we want our context menu to respond to
//        // here, we've chosen the list of team names (which in turn applies the context menu to
//        // each list item separately)
//        registerForContextMenu((ListView) findViewById(R.id.listView));
//
//        // we call this initiList function to fill in our list class variable with our venue names
//        initList();
//
//        // adapters are what we use to associate the list variable and its contents with the list view
//        ListView venueListView = (ListView) findViewById(R.id.listView);
//        SimpleAdapter simpleAdpt = new SimpleAdapter(this, venueList, android.R.layout.simple_list_item_1, new String[] {"venue"}, new int[] {android.R.id.text1});
//        venueListView.setAdapter(simpleAdpt);
//
//        // setOnItemClickListener tells the activity what to do when a list item is clicked on
//        venueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
//                                    long id) {
//                openVenueDetail(id);
//            }
//        });
//
//    }
//
//    // initList simply adds our venue names to the list variable
//    // in a real app, this would be where we query our database to retrieve the list of venues, but
//    // for the sake of our demo, this hard-coded data is sufficient
//    private void initList() {
//        venueList.add(createVenue("venue", "Venue 1: Backyard"));
//        venueList.add(createVenue("venue", "Venue 2: Pool"));
//        venueList.add(createVenue("venue", "Venue 3: Space 2435"));
//        venueList.add(createVenue("venue", "Venue 4: Duck Pond"));
//        venueList.add(createVenue("venue", "Venue 5: Photo-shoot Destination"));
//        venueList.add(createVenue("venue", "Venue 6: Butterfly Garden"));
//        venueList.add(createVenue("venue", "Venue 7: Patio"));
//    }
//
//    // this method helps us minimize the amount of repeat calls we need to make in initList to place
//    // a venue name into out list
//    private HashMap<String, String> createVenue(String key, String name) {
//        HashMap<String, String> venue = new HashMap<String, String>();
//        venue.put(key, name);
//        return venue;
//    }
//
//    // openVenueDetail is called whenever a list item is clicked on
//    // it calls for an intent that starts up the venue detail activity and sends the venue's id over
//    // to the activity with the message variable declared at the top of the activity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
            ImageView userImage = (ImageView)rootView.findViewById(R.id.userImage);
            userImage.setImageResource(R.drawable.nina);
            TextView userDetailView = (TextView)rootView.findViewById(R.id.userDetail);
            userDetailView.setText("Nina Dobrev is our featured user this week.");
            return rootView;
        }
    }

    public void goToUser(View view){
        Intent userIntent = new Intent(this, UserDetail.class);
        startActivity(userIntent);
    }
    public void goToProperty(View view){
        Intent userIntent = new Intent(this, VenueDetail.class);
        startActivity(userIntent);
    }

    public void searchPage(View view){
        Intent userIntent = new Intent(this, SearchPage.class);
        startActivity(userIntent);
    }
}
