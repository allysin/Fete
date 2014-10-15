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

        /*String [] featureduserArray = {
                Userphoto - Username - Userlocation - other detail
        };*/
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
}
