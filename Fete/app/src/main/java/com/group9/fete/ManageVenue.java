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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class ManageVenue extends Activity {
    List<String> teamsList = new ArrayList<String>();
    private List<String> venueList = new ArrayList<String>();
    private TextView switchStatus;
    private Switch mySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_venue);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            initList();
// Get the message from the intent
            Intent intent = getIntent();
//String message = intent.getStringExtra(OverviewActivity.EXTRA_MESSAGE);
//int id = (int) Long.parseLong(message);
// Create the text view
            TextView textView = new TextView(this);
            textView.setTextSize(40);
//textView.setText(venueList.get(id));
            switchStatus = (TextView) findViewById(R.id.switchStatus);
            mySwitch = (Switch) findViewById(R.id.mySwitch);
//set the switch to ON
            mySwitch.setChecked(true);
//attach a listener to check for changes in state
            mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if(isChecked){
                        switchStatus.setText("Venue currently visible");
                    }else{
                        switchStatus.setText("Venue currently invisible");
                    }
                }
            });
//check the current state before we display the screen
            if(mySwitch.isChecked()){
                switchStatus.setText("Venue currently visible");
            }
            else {
                switchStatus.setText("Venue currently invisible");
            }
// Set the text view as the activity layout
            setContentView(textView);
        }
    }
    private void initList() {
        venueList.add("Venue 1");
        venueList.add("Venue 2");
        venueList.add("Venue 3");
        venueList.add("Venue 4");
        venueList.add("Venue 5");
        venueList.add("Venue 6");
        venueList.add("Venue 7");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manage_venue, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
// launch intent to settings screen
            return true;
        }
//TODO
// else if (id == R.id.action_logout) {
// launch intent to settings screen
// return true;
//}
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
            View rootView = inflater.inflate(R.layout.fragment_manage_venue, container, false);
            return rootView;
        }
    }
}