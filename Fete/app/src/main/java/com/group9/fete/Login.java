package com.group9.fete;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class Login extends Activity {

    public static final String PREFS_NAME = "AppPreferences";

    // Welcome screen variable in sp...only used when need to reset for system
    // final String welcomeScreenShownPref = "welcomeScreenShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            return rootView;
        }
    }


    public void login(View view){



        String userName = new String();
        EditText textView = (EditText) findViewById(R.id.userName);
        userName = textView.getText().toString();

        //access shared preferences and editor to put username string
        SharedPreferences mySP = getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySP.edit();

        editor.putString("UserName", userName);

        // If need to reset for testing purposes
        // editor.putBoolean(welcomeScreenShownPref, false);

        editor.apply();

        Intent userIntent = new Intent(this, HomePage.class);
        startActivity(userIntent);


    }

    public void signup(View view){
        Intent userIntent = new Intent(this, SignUp.class);
        startActivity(userIntent);
    }



}
