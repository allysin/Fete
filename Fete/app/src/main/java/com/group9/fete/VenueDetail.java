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

import com.group9.fete.model.GlobalData;
import com.group9.fete.model.User;
import com.group9.fete.model.Venue;


public class VenueDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_detail);
        PlaceholderFragment venueDetail = new PlaceholderFragment();
        venueDetail.setArguments(getIntent().getExtras());
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, venueDetail)
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.venue_detail, menu);
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
            final Activity cont = getActivity();
            //Get the global data instance and get the bundle passed with the intent
            GlobalData data = (GlobalData)(cont.getApplication());
            Bundle inputExtra = getArguments();
            //look for page arguments passed in the bundle
            int venueId = inputExtra.getInt(getString(R.string.venueIdParam));
            View rootView = inflater.inflate(R.layout.fragment_venue_detail, container, false);

            //get venue object used to fill the page
            Venue venue = data.GetVenue(venueId);

            TextView venueNameView = (TextView)rootView.findViewById(R.id.venueNameVDetail);
            venueNameView.setText(venue.GetVenueName());
            TextView venueDetailView = (TextView)rootView.findViewById(R.id.venueDetailVDetail);
            venueDetailView.setText(venue.GetVenueDescription());
            TextView ownerNameView = (TextView)rootView.findViewById(R.id.userNameVDetail);
            int ownerId = venue.GetOwnerID();
            User user = data.GetUser(ownerId);
            ownerNameView.setText(user.GetUserName());
            ImageView userImageView = (ImageView)rootView.findViewById(R.id.userImageVDetail);
            int userImageId = getResources().getIdentifier(user.GetUserImage(), "drawable", cont.getPackageName());
            userImageView.setImageResource(userImageId);
            ImageView venueImageView = (ImageView)rootView.findViewById(R.id.venueImageVDetail);
            int resID = getResources().getIdentifier(venue.GetVenueImage(), "drawable", cont.getPackageName());
            venueImageView.setImageResource(resID);
            //View reviewSection = rootView.findViewById(R.id.reviewSectionVDetail);
            //bindReviews(inflater, reviewSection, venue);
            getActivity().getActionBar().setTitle(venue.GetVenueName());
            return rootView;
        }

        private void bindReviews(LayoutInflater inflater, View parentView, Venue v){
            View review = inflater.inflate(R.layout.review_layout, null);
        }
    }

    public void contact(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Fete Venue");
        intent.putExtra(Intent.EXTRA_TEXT, "I saw your Property on fete.");

        startActivity(Intent.createChooser(intent, "Contact Owner"));
    }

    public void goToPropertyLeft(View view, int currentVenueId, int totalVenueCount){
        Intent userIntent = new Intent(this, VenueDetail.class);
        int venueId = 5;
        userIntent.putExtra(getString(R.string.venueIdParam), venueId);
        startActivity(userIntent);
    }

    public void goToPropertyRight(View view, int currentVenueId, int totalVenueCount){
        Intent userIntent = new Intent(this, VenueDetail.class);
        int venueId = currentVenueId++;
        if (venueId>totalVenueCount-1){
            venueId = 0;
        }
        userIntent.putExtra(getString(R.string.venueIdParam), venueId);
        startActivity(userIntent);
    }
}
