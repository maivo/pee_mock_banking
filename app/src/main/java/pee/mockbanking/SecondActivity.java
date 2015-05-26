package pee.mockbanking;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import pee.mockbanking.R;


public class SecondActivity extends Activity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Calling Application class (see application tag in AndroidManifest.xml)
        final AppSession appSession = (AppSession) getApplicationContext();

        Log.i(TAG, "appSession: "+ appSession);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id ==android.R.id.home) {
            finish();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void handleGoMainActivity(){
        Log.i(TAG, "inside handleGoMainActivity");

        //Intent intent = new Intent(this, MainActivity.class);

        // calling an activity using <intent-filter> action name
        //  Intent intent = new Intent("com.hmkcode.android.ANOTHER_ACTIVITY");

        //startActivity(intent);
        finish();

    }

}
