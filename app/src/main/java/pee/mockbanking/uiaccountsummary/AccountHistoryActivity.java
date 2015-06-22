package pee.mockbanking.uiaccountsummary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.TextView;

import pee.mockbanking.R;
import pee.mockbanking.mb.Account;
import pee.mockbanking.ui.AppSession;


public class AccountHistoryActivity extends Activity {

    private static final String TAG = "WeatherDetailActivity";

    ImageButton imgWeatherIcon;
    TextView tvTitle;
    TextView tvBalance;
    TextView tvAvailableBalance;
    TextView tvStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailpage);

        //set title
        setTitle("WeatherDetailActivity");

        //retrieveWeatherData
        final AppSession appSession = (AppSession) getApplicationContext();
        Account account = appSession.getAccount();

        Log.d(TAG, "account: "+account);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
