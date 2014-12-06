//Allyson, Michael, and Anubhav implemented simple adapter for list view of unfiltered search results

package com.group9.fete;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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


    int venueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        GlobalData data = ((GlobalData)getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_search);


        registerForContextMenu((ListView) findViewById(R.id.listView));


        String query = new String();
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
           query = intent.getStringExtra(SearchManager.QUERY);
            //set title bar text to the text query
            getActionBar().setTitle("Search Results for: " + query);
        }

        //populate list by calling initList method
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



        //set string values of list layout ids that will recieve data
        String[] from = { "img","name","description", "id" };

        //set actual ids of xml layout where data will go
        int[] to = { R.id.img,R.id.name,R.id.description, R.id.venueId};

        //create new object adapter
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), venuesList, R.layout.listview_layout, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(R.id.listView);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idText = (TextView) view.findViewById(R.id.venueId);
               venueId = Integer.parseInt(idText.getText().toString());

                openVenueDetail(venueId);
;
            }
        });





    }

    private void initList(GlobalData data, String query){
        List<Venue> featuredVenues = data.GetVenues();



        for (int i=0;i<featuredVenues.size();i++ ) {
            Venue v = featuredVenues.get(i);

            if(v.GetVenueName().contains(query) == true){
                //make sure this text is gone
                TextView noResults = (TextView) findViewById(R.id.noResults);
                noResults.setVisibility(View.INVISIBLE);
                //get venue name, venueID, venuedescription, and venue image
                String vName = v.GetVenueName();
                int vId = v.GetID();
                String mDrawableName = v.GetVenueImage();
                int resID = getResources().getIdentifier(mDrawableName , "drawable", getPackageName());
                String vDescription = v.GetVenueDescription();


                //map data to xml layout
                HashMap<String, String> venue = new HashMap<String,String>();
                venue.put("img", Integer.toString(resID));
                venue.put("name", vName);
                venue.put("id", Integer.toString(vId));
                venue.put("description", vDescription);
                venuesList.add(venue);

            }
            else {

                TextView noResults = (TextView) findViewById(R.id.noResults);
                noResults.setVisibility(View.VISIBLE);
                noResults.setText("No results found for your query. Please search again");

            }



        }


    }





    public void openVenueDetail(int venueId) {
        Intent detailIntent = new Intent(this, VenueDetail.class);
        detailIntent.putExtra("venueId", venueId);
        startActivity(detailIntent);
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
