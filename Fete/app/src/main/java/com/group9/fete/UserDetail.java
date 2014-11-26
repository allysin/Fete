package com.group9.fete;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.SearchView;
import android.widget.TextView;


public class UserDetail extends Activity {



    SharedPreferences mPrefs;
    public final static String EXTRA_MESSAGE = "com.group9.fete.UserDetail.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //access sharedpreferences to get username stored at login
        SharedPreferences mySP = getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);

        String user =  mySP.getString("UserName", "");

        Log.i("user", user);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_detail, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem swi= menu.findItem(R.id.action_search);

        SearchView sw= (SearchView) swi.getActionView();
        sw.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        sw.setIconifiedByDefault(true);

        sw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(UserDetail.this, TestSearch.class);
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
            View rootView = inflater.inflate(R.layout.fragment_user_detail, container, false);
            Bundle arguments = getArguments();

            TextView textView = (TextView) rootView.findViewById(R.id.userName);
            Button editButton = (Button) rootView.findViewById(R.id.edit);
//
//            set logged in user name to userdetail name and also show edit profile button
            if (arguments != null && arguments.getString("LoggedUser")!= ""){
                textView.setText(arguments.getString("LoggedUser", ""));
                editButton.setVisibility(View.VISIBLE);

            }






            return rootView;
        }
    }

    public void goToProperty(View view){
        Intent userIntent = new Intent(this, VenueDetail.class);
        startActivity(userIntent);
    }

    public void goTest (View view){
        Intent intent = new Intent(this, TestSearch.class);
        startActivity(intent);
    }

    public void goEdit (View view){
        Intent intent = new Intent(this, EditUserProfile.class);

        TextView textview = (TextView) findViewById(R.id.userName);
        String user = textview.toString();
        intent.putExtra(EXTRA_MESSAGE, user);
        Log.i("userNamePassed to Edit", user);

        startActivity(intent);
    }



}
