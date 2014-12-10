package com.group9.fete;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group9.fete.model.GlobalData;
import com.group9.fete.model.User;
import com.group9.fete.model.Venue;

import java.util.List;


public class UserDetail extends Activity {
    SharedPreferences mPrefs;
    public final static String EXTRA_MESSAGE = "com.group9.fete.UserDetail.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        PlaceholderFragment userDetailFragment = new PlaceholderFragment();
        userDetailFragment.setArguments(getIntent().getExtras());
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, userDetailFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_detail, menu);
        return super.onCreateOptionsMenu(menu);
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
            GlobalData data = (GlobalData) (this.getActivity().getApplication());
            //get the bundle passed to the fragment from the activity
            Bundle inputExtra = getArguments();
            //look for page arguments passed in the bundle
            int userId = inputExtra.getInt("userId");

//            //access sharedpreferences to get username stored at login
//            SharedPreferences mySP = cont.getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
//            String user =  mySP.getString("UserName", "");
//            Log.i("user", user);

            View rootView = inflater.inflate(R.layout.fragment_user_detail, container, false);
            User user = data.GetUser(userId);
            Log.i("user id is", String.valueOf(userId));
            TextView userNameTextView = (TextView) rootView.findViewById(R.id.userNameUDetail);
            String userName = user.GetUserName();
            String[] nameparts = userName.split(" ");
            String firstName = userName;
            if (nameparts.length > 1) {
                firstName = nameparts[0];
            }
            userNameTextView.setText(userName);
            TextView userDetailTextView = (TextView) rootView.findViewById(R.id.userDetailUDetail);
            userDetailTextView.setText(user.GetUserBio());
            Button editButton = (Button) rootView.findViewById(R.id.edit);

            ImageView userImageView = (ImageView)rootView.findViewById(R.id.userImageUDetail);

            int userImageId = getResources().getIdentifier(user.GetUserImage(), "drawable", cont.getPackageName());


            userImageView.setImageResource(userImageId);

            //set logged in user name to userdetail name and also show edit profile button
            if (inputExtra != null && inputExtra.getBoolean(getString(R.string.loggedUserParam)) == true) {
                editButton.setVisibility(View.VISIBLE);
            }

            getActivity().getActionBar().setTitle(firstName + "'s Profile");


            //fill favorite venues
            WindowManager wm = (WindowManager) cont.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            float ratio = width / (float) height;
            List<Venue> featuredVenues = data.GetVenues();
            Integer totalRows = 0;
            int itemsInRow = 2;
            if (featuredVenues.size() % itemsInRow > 0) {
                totalRows = (featuredVenues.size() / itemsInRow) + 1;
            } else {
                totalRows = featuredVenues.size() / itemsInRow;
            }
            LinearLayout venueTable = (LinearLayout) rootView.findViewById(R.id.venueTable);
            for (int i = 0; i < totalRows; i++) {
                //create tablerows dynamically, add imageviews to it filled with appropiate images
                int rowLimit = featuredVenues.size() - (i * itemsInRow);
                if (rowLimit > itemsInRow) {
                    rowLimit = itemsInRow;
                }
                LinearLayout rowLayout = new LinearLayout(cont);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                rowLayout.setWeightSum(10);
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                rowLayout.setLayoutParams(lp);
                int imageSize = 0;
                if (ratio > 1) {
                    imageSize = (width / 3);
                } else {
                    imageSize = height / 4;
                }
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, imageSize);

                //rowLayout.setWeightSum(1f);
                //rowLayout.setLayoutParams(param);
                param.weight = 5f;
                param.gravity = Gravity.CENTER;
                for (int j = 0; j < rowLimit; j++) {
                    //View featuredVenue = inflater.inflate(R.layout.featured_venue_layout, null, false);
                    //ImageView customImageView = (ImageView)featuredVenue.findViewById(R.id.venueImage);
                    ImageView customImageView = new ImageView(cont);
                    Venue v = featuredVenues.get(i * itemsInRow + j);
                    final int venueId = v.GetID();
                    int resID = getResources().getIdentifier(v.GetVenueImage(), "drawable", cont.getPackageName());
                    customImageView.setImageResource(resID);
                    customImageView.setPadding(10, 10, 10, 10);
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


            return rootView;
        }

        public void goToProperty(View view, Context cont, int uniqueIdentifier) {
            Intent detailIntent = new Intent(cont, VenueDetail.class);
            detailIntent.putExtra(getString(R.string.venueIdParam), uniqueIdentifier);
            startActivity(detailIntent);
        }


    }
    public void goTest (View view){
        Intent intent = new Intent(this, TestSearch.class);
        startActivity(intent);
    }


}
