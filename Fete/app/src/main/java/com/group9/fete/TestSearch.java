//Allyson, Michael, and Anubhav implemented simple adapter for list view of unfiltered search results

package com.group9.fete;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.group9.fete.model.GlobalData;
import com.group9.fete.model.Venue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestSearch extends Activity  {



    public final static String EXTRA_MESSAGE = "com.group9.fete.TestSearch.MESSAGE";

    List<Map<String, String>> venuesList = new ArrayList<Map<String,String>>();

    List<Map<String, String>> usersList = new ArrayList<Map<String,String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        GlobalData data = ((GlobalData)getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search);

        registerForContextMenu((ListView) findViewById(R.id.listView));
        TextView textView = (TextView) findViewById(R.id.status_text);







        String query = new String();
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
           query = intent.getStringExtra(SearchManager.QUERY);
            textView.setText("You Searched for " + query);

        }

        if(query != "" && venuesList.isEmpty() == true){
            initList(data, query);
        }
        else {
            Log.e("TestSearch.java", "No query variable");
        }


//        final ListView venueListView = (ListView) findViewById(R.id.listView);
//        SimpleAdapter simpleAdpt = new SimpleAdapter(this, venuesList, android.R.layout.simple_list_item_1, new String[] {"venue"}, new int[] {android.R.id.text1});
//        venueListView.setAdapter(simpleAdpt);
//
//        venueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
//                                    long id) {
//
//              openVenueDetail(id);
//            }
//        });


        List<Venue> featuredVenues = data.GetVenues();



        String[] from = { "img","name","description" };

        int[] to = { R.id.img,R.id.name,R.id.description};

        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), venuesList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);


//        final ListView vList =(ListView)findViewById(R.id.listView);
//        CustomList adapter = new
//                CustomList(TestSearch.this, venuesList, imageId);
//        vList.setAdapter(adapter);
//        vList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(TestSearch.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
//            }
//        });



    }

    private void initList(GlobalData data, String query){
        List<Venue> featuredVenues = data.GetVenues();
        List<Venue> filteredVenues = new ArrayList<Venue>();


        for (int i=0;i<featuredVenues.size();i++ ) {
            Venue v = featuredVenues.get(i);

            if(v.GetVenueName().contains(query) == true){
                String vName = v.GetVenueName();
                int vId = v.GetID();
                String mDrawableName = v.GetVenueImage();
                int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                String vDescription = v.GetVenueDescription();
                String output = vName;
                //venuesList.add(createVenue("venue", output));


                HashMap<String, String> venue = new HashMap<String,String>();
                venue.put("img", Integer.toString(resID));
                venue.put("name", vName);
                venue.put("description", vDescription);
                venuesList.add(venue);

            }



        }


    }


//    private HashMap<String, String> createVenue(String key, String name) {
//        HashMap<String, String> venue = new HashMap<String, String>();
//        venue.put(key, name);
//        venue.put(key, venueImage);
//        return venue;
//    }





    public void openVenueDetail(long id) {
        Intent intent = new Intent(this, VenueDetail.class);
        int message = (int) id;
        String vvv = String.valueOf(id);
        Log.i("value id", vvv);
        intent.putExtra("venueId", message);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


        return true;
    }



//
//    private void setupSearchView(MenuItem searchItem) {
//
//        if (isAlwaysExpanded()) {
//            mSearchView.setIconifiedByDefault(false);
//        } else {
//            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
//                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//        }
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null) {
//            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
//
//            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//            for (SearchableInfo inf : searchables) {
//                if (inf.getSuggestAuthority() != null
//                        && inf.getSuggestAuthority().startsWith("applications")) {
//                    info = inf;
//                }
//            }
//            mSearchView.setSearchableInfo(info);
//        }
//
//        mSearchView.setOnQueryTextListener(this);
//    }
//
//    public boolean onQueryTextChange(String newText) {
//        mStatusView.setText("Query = " + newText);
//        return false;
//    }
//
//
//    public boolean onQueryTextSubmit(String query) {
//        Intent intent = new Intent(this, SearchPage.class);
//        String message = query;
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//
//        mStatusView.setText("Query = " + query + " : submitted");
//        return true;
//
//    }

//    public boolean onClose() {
//        mStatusView.setText("Closed!");
//        return false;
//    }

    protected boolean isAlwaysExpanded() {
        return false;
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
}
