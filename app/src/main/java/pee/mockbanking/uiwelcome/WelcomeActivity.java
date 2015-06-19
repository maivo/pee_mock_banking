package pee.mockbanking.uiwelcome;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pee.mockbanking.R;
import pee.mockbanking.mb.Account;
import pee.mockbanking.mb.AuthenticateUserResponse;
import pee.mockbanking.mb.MbAsGetAccountsResponseParser;
import pee.mockbanking.mb.MbClient;
import pee.mockbanking.mb.MbEndPoints;
import pee.mockbanking.mb.MbFailure;
import pee.mockbanking.mb.MbSsAuthenticateUserResponseParser;
import pee.mockbanking.ui.LoginActivity;
import pee.mockbanking.uimain.MainActivity;
import pee.mockbanking.util.ActivityUtils;


public class WelcomeActivity extends Activity {

    private static final String TAG = "LoginActivity";


    // List items
    ListView lvTitleCollection;
    WelcomeBinderData adapter = null;
    List<String> titleCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //set title
        setTitle(R.string.welcome_title);

        titleCollection = new ArrayList<String>();

        titleCollection.add("Sign In");
        titleCollection.add("Contact Us");
        titleCollection.add("Current Rates");
        titleCollection.add("Find Branch");
        titleCollection.add("About");


        WelcomeBinderData bindingData = new WelcomeBinderData(this, titleCollection);


        lvTitleCollection = (ListView) findViewById(R.id.list);

        Log.i("BEFORE", "<<------------- Before SetAdapter-------------->>");

        lvTitleCollection.setAdapter(bindingData);

        Log.i("AFTER", "<<------------- After SetAdapter-------------->>");

        // Click event for single list row
        lvTitleCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.i(TAG, "inside onClick");
                if(position == 0){
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                    return;
                }
                if(position == 1){
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                    return;
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_go) {
            //handleGoSecondActivity();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }




}




