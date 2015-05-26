package pee.mockbanking;

import android.app.Application;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class AppSession extends Application {
    String mUserName;
    String mPassword;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public String toString() {
        return "AppSession{" +
                "mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
