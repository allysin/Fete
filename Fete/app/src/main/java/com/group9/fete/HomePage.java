package com.group9.fete;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.group9.fete.adapter.NavDrawerListAdapter;
import com.group9.fete.model.GlobalData;
import com.group9.fete.model.NavDrawerItem;

import java.util.ArrayList;

public class HomePage extends Activity {
    ImageView image;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GlobalData data = (GlobalData)getApplicationContext();
        data.SetData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));


        // Recycle the typed array
        navMenuIcons.recycle();

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        //if (savedInstanceState == null) {
        //    getFragmentManager().beginTransaction()
        //            .add(R.id.container, new PlaceholderFragment())
        //            .commit();
        //}
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }


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

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new PlaceholderFragment();
                break;
            case 1:
                fragment = new UserDetail.PlaceholderFragment();
                break;
            case 2:
                fragment = new ManageVenue.PlaceholderFragment();
                break;
            case 3:
            case 4:
                fragment = new SettingsFragment();
                break;
            case 5:
                fragment = new Login.PlaceholderFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

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
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
        // toggle nav drawer on selecting action bar app icon/title\
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                Intent userIntent = new Intent(this, SearchPage.class);
                startActivity(userIntent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
            Context cont = getActivity();
            View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
            ImageView userImage = (ImageView)rootView.findViewById(R.id.userImage);
            userImage.setImageResource(R.drawable.nina);
            TextView userDetailView = (TextView)rootView.findViewById(R.id.userDetail);
            userDetailView.setText("Nina Dobrev is our featured user this week. She likes to knit and ride bicycles" +
                    "down by the river whenever...");
            String filenameVenueImage = getString(R.string.venue_image_names);
            String fileNumberTotalString = getString(R.string.number_of_venues);
            Integer totalFiles = Integer.parseInt(fileNumberTotalString);
            Integer totalRows = totalFiles/3;
            TableLayout venueTable = (TableLayout)rootView.findViewById(R.id.venueTable);
            for (int i=0;i<totalRows;i++ )
            {
                TableRow row = new TableRow(cont);
                for (int j=0;j<3;j++) {
                    View imageView = new ImageView(cont);
                    String filename = filenameVenueImage + Integer.toString(3*i + j);
                    int resID = getResources().getIdentifier(filename, "drawable", cont.getPackageName());
                    ((ImageView) imageView).setImageResource(resID);
                    ((ImageView) imageView).layout(10, 10, 10, 10);
                    row.addView(imageView, 150, 150);
                }
                venueTable.addView(row);
            }
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
