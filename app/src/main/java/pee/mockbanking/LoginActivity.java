package pee.mockbanking;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pee.mockbanking.R;


public class LoginActivity extends Activity {

    private static final String TAG = "SignUpActivity";
    private EditText etUserName;
    private EditText etPassword;
    private Button btnSignIn;
    private View progressView;
    private View loginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set title
        setTitle(R.string.activity_login_title);

        //
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignIn(view);
            }
        });

        loginFormView = findViewById(R.id.login_form);
        progressView = findViewById(R.id.pbLoginProgress);

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
        //assert: have valid value for userName and password

        //hide keyboard
        hideKeyboard();

        // show progress spinner, and start background task to login
        showProgress(true);

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



}
