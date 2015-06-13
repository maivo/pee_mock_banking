package pee.mockbanking.ui;

import android.app.Application;

import java.util.List;

import pee.mockbanking.mb.Account;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class AppSession extends Application {
    String userName;
    String password;
    String channelSessionId;
    String challengeQuestion;
    String challengeQuestionId;
    List<Account> accountList;

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
        this.channelSessionId = channelSessionId;
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

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
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
