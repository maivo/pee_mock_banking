package pee.mockbanking.mb;

import android.app.Application;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class AuthenticateUserResponse extends Application {
    String mChannelSessionId;

    //eg:<ns1:entry ns1:key="ChallengeQuestion" xsi:type="xsd:string">How many TVs are in your home?</ns1:entry>
    String challengeQuestion;
    String challengeQuestionId;

    public String getChannelSessionId() {
        return mChannelSessionId;
    }

    public void setChannelSessionId(String channelSessionId) {
        mChannelSessionId = channelSessionId;
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
        return "AuthenticateUserResponse{" +
                "mChannelSessionId='" + mChannelSessionId + '\'' +
                ", challengeQuestion='" + challengeQuestion + '\'' +
                ", challengeQuestionId='" + challengeQuestionId + '\'' +
                '}';
    }


    public boolean isChallengeQuestion(){
        return challengeQuestionId != null;
    }
}
