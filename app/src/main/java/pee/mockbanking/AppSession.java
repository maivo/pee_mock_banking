package pee.mockbanking;

import android.app.Application;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class AppSession extends Application {
    String mUserName;
    String mPassword;
    String mChannelSessionId;

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

    public String getChannelSessionId() {
        return mChannelSessionId;
    }

    public void setChannelSessionId(String channelSessionId) {
        mChannelSessionId = channelSessionId;
    }

    @Override
    public String toString() {
        return "AppSession{" +
                "mUserName='" + mUserName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mChannelSessionId='" + mChannelSessionId + '\'' +
                '}';
    }
}
