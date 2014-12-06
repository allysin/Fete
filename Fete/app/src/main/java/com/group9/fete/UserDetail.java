package com.group9.fete;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.group9.fete.model.GlobalData;
import com.group9.fete.model.User;


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
            GlobalData data = (GlobalData)(this.getActivity().getApplication());
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
            TextView userNameTextView = (TextView) rootView.findViewById(R.id.userNameUDetail);
            String userName = user.GetUserName();
            String[] nameparts = userName.split(" ");
            String firstName = userName;
            if (nameparts.length>1){
                firstName = nameparts[0];
            }
            userNameTextView.setText(userName);
            TextView userDetailTextView = (TextView) rootView.findViewById(R.id.userDetailUDetail);
            userDetailTextView.setText(user.GetUserBio());
            Button editButton = (Button) rootView.findViewById(R.id.edit);

            //set logged in user name to userdetail name and also show edit profile button
            if (inputExtra != null && inputExtra.getBoolean(getString(R.string.loggedUserParam))== true){
                editButton.setVisibility(View.VISIBLE);
            }

            getActivity().getActionBar().setTitle(firstName + "'s Profile");
            return rootView;
        }
    }

    public void goToProperty(View view){
        Intent userIntent = new Intent(this, VenueDetail.class);
        int venueId = 1;
        userIntent.putExtra(getString(R.string.venueIdParam), venueId);
        startActivity(userIntent);
    }

    public void goTest (View view){
        Intent intent = new Intent(this, TestSearch.class);
        startActivity(intent);
    }

    public void goEdit (View view){
        Intent intent = new Intent(this, EditUserProfile.class);

        TextView textview = (TextView) findViewById(R.id.userNameUDetail);
        String user = textview.toString();
        intent.putExtra(EXTRA_MESSAGE, user);
        Log.i("userNamePassed to Edit", user);

        startActivity(intent);
    }
}
