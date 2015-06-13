package pee.mockbanking.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import pee.mockbanking.R;
import pee.mockbanking.mb.Account;


/**
 * Created by pvu_asus on 11/06/2015.
 */
public class AccountSummaryActivity extends Activity{
    private static final String TAG = "AccountSummaryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_summary);

        //show back button
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //set title
        setTitle(R.string.account_summary_title);

        final AppSession appSession = (AppSession) getApplicationContext();
        List<Account> accountList = null;
        accountList = appSession.getAccountList();
        Log.i(TAG, "accountList: \n" + Account.toString(accountList));
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
