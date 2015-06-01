package pee.mockbanking.mb;

import android.app.Application;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class AuthenticateUserResponse extends Application {
    String mChannelSessionId;

    public String getChannelSessionId() {
        return mChannelSessionId;
    }

    public void setChannelSessionId(String channelSessionId) {
        mChannelSessionId = channelSessionId;
    }

    @Override
    public String toString() {
        return "AuthenticateUserResponse{" +
                "mChannelSessionId='" + mChannelSessionId + '\'' +
                '}';
    }
}
