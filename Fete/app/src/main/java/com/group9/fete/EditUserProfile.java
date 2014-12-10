package com.group9.fete;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.group9.fete.model.GlobalData;
import com.group9.fete.model.User;


public class EditUserProfile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_user_profile, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_edit_user_profile, container, false);

            final Activity cont = getActivity();

            GlobalData data = (GlobalData) (this.getActivity().getApplication());

            //get the bundle passed to the fragment from the activity
            Bundle inputExtra = getArguments();
            //look for page arguments passed in the bundle
            int userId = inputExtra.getInt("userId");

            User user = data.GetUser(userId);
            EditText userNameTextView = (EditText) rootView.findViewById(R.id.userName);
            String userName = user.GetUserName();
            String[] nameparts = userName.split(" ");
            String firstName = userName;
            if (nameparts.length > 1) {
                firstName = nameparts[0];
            }
            userNameTextView.setText(userName);
            TextView userDetailTextView = (TextView) rootView.findViewById(R.id.userDetailUDetail);
            userDetailTextView.setText(user.GetUserBio());


        //intent passed username via extra message. get it to change the text view
//        String message  = getActivity().getIntent().getExtras().getString(HomePage.EXTRA_MESSAGE);
//        Log.i("message", message);
//        EditText user = (EditText)rootView.findViewById(R.id.userName);
//        user.setText(message);

            return rootView;
        }
    }

//old way of doing things
//    public void saveProfile(View view){
//        //alert dialog confirming profile saved
////        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info).setMessage("Profile Saved!.").setPositiveButton("ok!", new DialogInterface.OnClickListener() {
////            public void onClick(DialogInterface dialog, int which) {
////                dialog.dismiss();
////            }
////        }).show();
//
//
//
//        SharedPreferences mySP = getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = mySP.edit();
//        EditText user = (EditText)findViewById(R.id.userName);
//        final String newUserName = user.getText().toString();
//        Log.i("edited name", newUserName);
//
//        //not quite fixed but will make better TODO
//
//        if(newUserName.length() > 2){
//            editor.remove("UserName");
//            editor.putString("UserName", newUserName);
//            editor.commit();
//        }
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
//                new ContextThemeWrapper(this, R.style.MyAlertDialogTheme));
//        alertDialog.setMessage("Profile Saved!.");
//        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                FragmentManager fragmentManager = getFragmentManager();
//                Fragment fragment = new UserDetail.PlaceholderFragment();
//                Bundle b = new Bundle();b.putInt(getString(R.string.userIdParam), 1);
//                b.putBoolean(getString(R.string.loggedUserParam), true);
//                b.putString("LoggedUser", newUserName);
//                fragment.setArguments(b);
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, fragment).commit();
//                return;
//            }
//        });
//
//        alertDialog.show();
//
//
//
//    }


}
