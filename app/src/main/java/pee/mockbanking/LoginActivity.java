package pee.mockbanking;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import pee.mockbanking.mb.AuthenticateUserResponse;
import pee.mockbanking.mb.MbFailure;
import pee.mockbanking.mb.MbFault;
import pee.mockbanking.mb.MbClient;
import pee.mockbanking.util.ActivityUtils;


public class LoginActivity extends Activity {

    private static final String TAG = "SignUpActivity";

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
        ActivityUtils.hideKeyboard(this);

        // show progress spinner, and start background task to login
        ActivityUtils.showProgress(getApplicationContext(), progressView, loginFormView, true);
        Log.i(TAG, "calling handleSecurityServiceGetMultifactorSecurityInfo...");
        handleSecurityServiceGetMultifactorSecurityInfo();
    }


    private void handleSecurityServiceGetMultifactorSecurityInfo(){
        Log.i(TAG, "inside handleSecurityServiceGetMultifactorSecurityInfo");
        Context context = this.getApplicationContext();
        String  endPoint = MbClient.SECURITY_SERVICE_ENDPOINT;
        String  requestXml =  MbClient.getSecurityServiceGetMultifactorSecurityInfoRequestXml(context, userName, password);
        Log.i(TAG, "requestXml: \n" + requestXml);
        ResponseHandlerInterface responseHandler = new  MbSecurityServiceGetMultifactorSecurityInfoResponseHandler();
        MbClient.post(context, endPoint, requestXml, responseHandler);
    }

    private void handleSecurityServiceAuthenticateUser(){
        Context context = this.getApplicationContext();
        String  endPoint = MbClient.SECURITY_SERVICE_ENDPOINT;
        String  requestXml =  MbClient.getSecurityServiceAuthenticateUserRequestXml(context, userName, password);
        Log.i(TAG, "requestXml: \n" + requestXml);
        ResponseHandlerInterface responseHandler = new  MbSecurityServiceAuthenticateUserResponseHandler();
        MbClient.post(context, endPoint, requestXml, responseHandler);
    }


    private class MbSecurityServiceAuthenticateUserResponseHandler extends AsyncHttpResponseHandler{

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String responseXml = new String(bytes);
            Log.i(TAG, "MbSecurityServiceAuthenticateUserResponseHandler.onSuccess. responseXml: \n" + responseXml);

            //parse the authenticateUserResponse
            AuthenticateUserResponse authenticateUserResponse = null;
            InputStream inputStream = new ByteArrayInputStream(bytes);
            MbSecurityServiceAuthenticateUserResponseParser parser = new MbSecurityServiceAuthenticateUserResponseParser();
            authenticateUserResponse = parser.parse(inputStream);
            Log.i(TAG, "authenticateUserResponse: \n" + authenticateUserResponse);

            //add relevant info to appSession
            final AppSession appSession = (AppSession) getApplicationContext();
            appSession.setUserName(userName);
            appSession.setPassword(password);
            appSession.setChannelSessionId(authenticateUserResponse.getChannelSessionId());


            //hide progress bar
            ActivityUtils.showProgress(getApplicationContext(), progressView, loginFormView,false);
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            handleMbFailure(bytes, throwable);
        }
    }

    private class MbSecurityServiceAuthenticateUserResponseParser {
        AuthenticateUserResponse authenticateUserResponse;

        private String text;

        public MbSecurityServiceAuthenticateUserResponseParser() {
            authenticateUserResponse = new AuthenticateUserResponse();
        }



        public AuthenticateUserResponse parse(InputStream is) {
            XmlPullParserFactory factory = null;
            XmlPullParser parser = null;
            try {
                factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                parser = factory.newPullParser();

                parser.setInput(is, null);

                int eventType = parser.getEventType();
                String employeeIndex = null;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = parser.getName();
                    switch (eventType) {
                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase("channelSessionId")) {
                                // add employee object to list
                                authenticateUserResponse.setChannelSessionId(text);
                            }
                            break;

                        default:
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return authenticateUserResponse;
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


}




