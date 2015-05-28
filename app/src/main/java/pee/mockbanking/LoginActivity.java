package pee.mockbanking;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pee.mockbanking.R;


public class LoginActivity extends Activity {

    private static final String TAG = "SignUpActivity";
    private EditText etUserName;
    private EditText etPassword;
    private Button bSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set title
        setTitle(R.string.activity_login_title);

        //
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bSignIn = (Button) findViewById(R.id.bSignIn);
        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignIn(view);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSignIn(View view) {
        Log.i(TAG, "inside onSignIn");

        //clear error
        etUserName.setError(null);
        etPassword.setError(null);

        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        Log.i(TAG, "userName: "+userName);
        Log.i(TAG, "password: "+password);

        boolean isUserNameInvalid = false;
        boolean isPasswordInvalid = false;

        //validateUserName
        if (TextUtils.isEmpty(userName)) {
            Log.i(TAG, "TextUtils.isEmpty(userName)");
            etUserName.setError(getString(R.string.input_empty));
            isUserNameInvalid = true;
        }

        //validatePassword
        if (TextUtils.isEmpty(password)) {
            Log.i(TAG, "TextUtils.isEmpty(password)");
            etPassword.setError(getString(R.string.input_empty));
            isPasswordInvalid = true;

        }
        if(isUserNameInvalid){
            etUserName.requestFocus();
            return;
        }

        if(isPasswordInvalid){
            etPassword.requestFocus();
            return;
        }
    }

}
