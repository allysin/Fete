package com.group9.fete;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.group9.fete.adapter.NavDrawerListAdapter;
import com.group9.fete.model.GlobalData;
import com.group9.fete.model.NavDrawerItem;

import java.util.ArrayList;

public class HomePage extends Activity {


    public GlobalData AppData;
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

    ///welcome screen
    final String welcomeScreenShownPref = "welcomeScreenShown";
    SharedPreferences mPrefs;

    public final static String EXTRA_MESSAGE = "com.group9.fete.HomePage.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Make the homepage activity the current activity
        setContentView(R.layout.activity_home_page);

        //get shared preferences
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //set shared preference for welcome screen to false
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        //check to see if welcomescreen SP exists, if not, display it
        if (!welcomeScreenShown) {

            //get details for alert dialog from strings.xml
            String whatsNewTitle = getResources().getString(R.string.About);
            String whatsNewText = getResources().getString(R.string.AboutText);

            //build content of alert dialogue
            new AlertDialog.Builder(this).setIcon(R.drawable.ic_fete).setTitle(whatsNewTitle).setMessage(whatsNewText).setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            //change value of welcomescreen in SP and commit changes

            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit(); // Very important to save the preference
        }

           //sets title for nav drawer
//        mTitle = mDrawerTitle = "fete";
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
        // My Profile
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // My Venues
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Settings
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // About
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // Log Out
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));


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

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                //Fragment for home page is returned when Home is selected on Drawer
                fragment = new HomeFragment();
                break;
            case 1:
                //Fragment for UserDetail page is returned when My Profile is selected
                fragment = new UserDetail.PlaceholderFragment();
                break;
            case 2:
                //Fragment for ManageVenue page is returned when My Venues is selected
                fragment = new ManageVenue.PlaceholderFragment();
                break;
            case 3:
                //Fragment for Settings page is returned when Settings is selected
                fragment = new SettingsFragment();
                break;
            case 4:
                //Fragment for About
                fragment = new About.PlaceholderFragment();
                break;
            case 5:
                //Intent is fired to go to Login activity on Logout
                Intent loginIntent = new Intent(this, Login.class);
                startActivity(loginIntent);
                break;

            default:
                break;
        }

        if (fragment != null) {
            //access sharedpreferences to get username stored at login
            SharedPreferences mySP = getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
            String user =  mySP.getString("UserName", "");

            Log.i("user", user);

            FragmentManager fragmentManager = getFragmentManager();

            //set username from shared preferences
            Bundle b = new Bundle();
            b.putString("LoggedUser", user);
            fragment.setArguments(b);

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem swi= menu.findItem(R.id.action_search);

        SearchView sw= (SearchView) swi.getActionView();
        sw.setQueryHint("Try Pool");

        sw.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        sw.setIconifiedByDefault(true);

        sw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(HomePage.this, TestSearch.class);
                Bundle bundle = new Bundle();
                bundle.putString("query", query);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Boolean flag = true;
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
            return flag;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                //When settings button is selected on the AppBar, settings page should show up
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.action_search:
                //When Search button is clicked, Textbox should appear. TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return flag;
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
        //override
        mTitle = "";
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
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            Context cont = getActivity();
//            GlobalData data = (GlobalData) getApplicationContext();
//            View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
//            ImageView userImage = (ImageView)rootView.findViewById(R.id.userImage);
//            userImage.setImageResource(R.drawable.nina);
////            TextView userDetailView = (TextView)rootView.findViewById(R.id.userDetail);
////            userDetailView.setText("Nina Dobrev is our featured user this week. She likes to knit and ride bicycles" +
////                    "down by the river whenever...");
//
//            //code will fetch images named poolimage with numbers concatenated
//            String filenameVenueImage = getString(R.string.venue_image_names);
//            String fileNumberTotalString = getString(R.string.number_of_venues);
//            Integer totalFiles = Integer.parseInt(fileNumberTotalString);
//            Integer totalRows = totalFiles/3;
//            TableLayout venueTable = (TableLayout)rootView.findViewById(R.id.venueTable);
//            for (int i=0;i<totalRows;i++ )
//            {
//                //create tablerows dynamically, add imageviews to it filled with appropiate images
//                TableRow row = new TableRow(cont);
//                for (int j=0;j<3;j++) {
//                    View imageView = new ImageView(cont);
//                    String filename = filenameVenueImage + Integer.toString(3*i + j);
//                    int resID = getResources().getIdentifier(filename, "drawable", cont.getPackageName());
//                    ((ImageView) imageView).setImageResource(resID);
//                    ((ImageView) imageView).layout(10, 10, 10, 10);
//                    //once the row is filled, all row to the table
//                    row.addView(imageView, 150, 150);
//                }
//                venueTable.addView(row);
//            }
//            return rootView;
//        }
//    }

    public void goToUser(View view){
        Intent userIntent = new Intent(this, UserDetail.class);
        startActivity(userIntent);
    }

    public void goToProperty(View view){
        Intent userIntent = new Intent(this, VenueDetail.class);
        startActivity(userIntent);
    }

    public void goToPropertyLeft(View view){
        Intent userIntent = new Intent(this, VenueDetail.class);
        int venueId = 5;
        userIntent.putExtra(getString(R.string.venueId), venueId);
        startActivity(userIntent);
    }

    public void goToPropertyRight(View view){
        Intent userIntent = new Intent(this, VenueDetail.class);
        int venueId = 3;
        userIntent.putExtra(getString(R.string.venueId), venueId);
        startActivity(userIntent);
    }

    public void searchPage(View view){
        Intent userIntent = new Intent(this, SearchPage.class);
        startActivity(userIntent);
    }

    public void goTest(View view){
        Intent userIntent = new Intent(this, TestSearch.class);
        startActivity(userIntent);
    }


    public void goEdit (View view){
        Intent intent = new Intent(this, EditUserProfile.class);

        TextView textview = (TextView) findViewById(R.id.userName);
        String user = textview.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, user);
        Log.i("userNamePassed to Edit", user);

        startActivity(intent);

    }


}
