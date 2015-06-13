package pee.mockbanking.ui;


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
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import pee.mockbanking.R;
import pee.mockbanking.mb.Account;
import pee.mockbanking.mb.AuthenticateUserResponse;
import pee.mockbanking.mb.MbAsGetAccountsResponseParser;
import pee.mockbanking.mb.MbEndPoints;
import pee.mockbanking.mb.MbFailure;
import pee.mockbanking.mb.MbClient;
import pee.mockbanking.mb.MbSsAuthenticateUserResponseParser;
import pee.mockbanking.util.ActivityUtils;


public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";

    private static final String DUMMY_CREDENTIALS = "admin:admin";
    private EditText etUserName;
    private EditText etPassword;
    private Button btnSignIn;
    private View progressView;
    private View loginFormView;

    private String userName;
    private String password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set title
        setTitle(R.string.title);

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

        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
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


        // show progress spinner, and start background task to login
        ActivityUtils.showProgress(getApplicationContext(), progressView, loginFormView, true);

        //hide keyboard
        ActivityUtils.hideKeyboard(this);

        Log.i(TAG, "calling handleSecurityServiceGetMultifactorSecurityInfo...");
        handleSecurityServiceGetMultifactorSecurityInfo();
    }


    private void handleSecurityServiceGetMultifactorSecurityInfo(){
        Log.i(TAG, "inside handleSecurityServiceGetMultifactorSecurityInfo");

        Context context = this.getApplicationContext();
        String  endPoint = MbEndPoints.getInstance().getSsGetMultifactorSecurityInfo();
        String  requestXml =  MbClient.getSsGetMultifactorSecurityInfoRequestXml(context, userName, password);
        Log.i(TAG, "requestXml: \n" + requestXml);
        ResponseHandlerInterface responseHandler = new  MbSecurityServiceGetMultifactorSecurityInfoResponseHandler();
        MbClient.post(context, endPoint, requestXml, responseHandler);
    }

    private void handleSecurityServiceAuthenticateUser(){
        Context context = this.getApplicationContext();

        String  endPoint = MbEndPoints.getInstance().getSsGetAuthenticateUser();
        String  requestXml =  MbClient.getSsAuthenticateUserRequestXml(context, userName, password);
        Log.i(TAG, "requestXml: \n" + requestXml);
        ResponseHandlerInterface responseHandler = new MbSsAuthenticateUserResponseHandler();
        MbClient.post(context, endPoint, requestXml, responseHandler);
    }

    private void handleAccountServiceGetAccounts(){
        Context context = this.getApplicationContext();
        final AppSession appSession = (AppSession) getApplicationContext();
        Log.i(TAG, "appSession: \n" + appSession);

        String  endPoint = MbEndPoints.getInstance().getAsGetAccounts();
        Log.i(TAG, "endPoint: \n" + endPoint);

        String  requestXml =  MbClient.getAsGetAccountsRequestXml(context, appSession);
        Log.i(TAG, "requestXml: \n" + requestXml);
        ResponseHandlerInterface responseHandler = new MbAsGetAccountsResponseHandler();
        MbClient.post(context, endPoint, requestXml, responseHandler);
    }


    private class MbSsAuthenticateUserResponseHandler extends AsyncHttpResponseHandler{

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String responseXml = new String(bytes);
            Log.i(TAG, "MbSecurityServiceAuthenticateUserResponseHandler.onSuccess. responseXml: \n" + responseXml);

            //parse the authenticateUserResponse
            AuthenticateUserResponse authenticateUserResponse = null;
            InputStream inputStream = new ByteArrayInputStream(bytes);
            MbSsAuthenticateUserResponseParser parser = new MbSsAuthenticateUserResponseParser();
            authenticateUserResponse = parser.parse(inputStream);
            Log.i(TAG, "authenticateUserResponse: \n" + authenticateUserResponse);

            //add relevant info to appSession
            final AppSession appSession = (AppSession) getApplicationContext();
            appSession.setUserName(userName);
            appSession.setPassword(password);
            appSession.setChannelSessionId(authenticateUserResponse.getChannelSessionId());
            appSession.setChallengeQuestion(authenticateUserResponse.getChallengeQuestion());
            appSession.setChallengeQuestionId(authenticateUserResponse.getChallengeQuestionId());

            //call getAccounts
            Log.i(TAG, "calling mb.accountService.getAccounts...");
            handleAccountServiceGetAccounts();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            handleMbFailure(bytes, throwable);
        }
    }

    private class MbSecurityServiceGetMultifactorSecurityInfoResponseHandler extends AsyncHttpResponseHandler{


        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String s = new String(bytes);
            Log.i(TAG, "onSuccess08. s: \n" + s);
            Log.i(TAG, "calling handleSecurityServiceAuthenticateUser...");
            handleSecurityServiceAuthenticateUser();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            handleMbFailure(bytes, throwable);
        }
    }

    private void handleMbFailure(byte [] bytes, Throwable throwable){
        Log.e(TAG, "error", throwable);
        MbFailure mbFailure = new MbFailure(bytes, throwable);
        ActivityUtils.showProgress(getApplicationContext(), progressView, loginFormView, false);
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Sign in Error")
                .setMessage(mbFailure.getDetailMsg())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private class MbAsGetAccountsResponseHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String responseXml = new String(bytes);
            Log.i(TAG, "MbAsGetAccountsResponseHandler.onSuccess. responseXml: \n" + responseXml);

            List<Account> accountList = null;
            InputStream inputStream = new ByteArrayInputStream(bytes);
            MbAsGetAccountsResponseParser parser = new MbAsGetAccountsResponseParser();
            accountList = parser.parse(inputStream);

            //add account list to session
            final AppSession appSession = (AppSession) getApplicationContext();
            appSession.setAccountList(accountList);

            Log.i(TAG, "accountList: \n" + Account.toString(accountList));

            //hide progress bar
            ActivityUtils.showProgress(getApplicationContext(), progressView, loginFormView, false);

            //go AccountSummary activity
            Intent intent = new Intent(LoginActivity.this, AccountSummaryActivity.class);

            startActivity(intent);
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            handleMbFailure(bytes, throwable);
        }

    }


}




