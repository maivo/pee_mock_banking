package pee.mockbanking;

import android.app.Application;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class AppSession extends Application {
    String userName;
    String password;
    String channelSessionId;
    String challengeQuestion;
    String challengeQuestionId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannelSessionId() {
        return channelSessionId;
    }

    public void setChannelSessionId(String channelSessionId) {
        channelSessionId = channelSessionId;
    }


    public String getChallengeQuestion() {
        return challengeQuestion;
    }

    public void setChallengeQuestion(String challengeQuestion) {
        this.challengeQuestion = challengeQuestion;
    }

    public String getChallengeQuestionId() {
        return challengeQuestionId;
    }

    public void setChallengeQuestionId(String challengeQuestionId) {
        this.challengeQuestionId = challengeQuestionId;
    }

    @Override
    public String toString() {
        return "AppSession{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", channelSessionId='" + channelSessionId + '\'' +
                ", challengeQuestion='" + challengeQuestion + '\'' +
                ", challengeQuestionId='" + challengeQuestionId + '\'' +
                '}';
    }
}
