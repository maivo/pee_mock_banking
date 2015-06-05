package pee.mockbanking.mb;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by pvu_asus on 03/06/2015.
 */
public class MbSsAuthenticateUserResponseParser {

    private static final String TAG = "MbSsA..ResponseParser";
    AuthenticateUserResponse authenticateUserResponse;

    private String text;

    public MbSsAuthenticateUserResponseParser() {
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

            String entryKeyValue = null;
            boolean isChallenqeQuestionEntry = false;
            boolean isChallenqeQuestionIdEntry = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("entry")) {
                            entryKeyValue = parser.getAttributeValue(null, "key");
                            if("ChallengeQuestion".equalsIgnoreCase(entryKeyValue)){
                                isChallenqeQuestionEntry = true;

                            }
                            if("ChallengeQuestionID".equalsIgnoreCase(entryKeyValue)){
                                isChallenqeQuestionIdEntry = true;
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("channelSessionId")) {
                            // add employee object to list
                            authenticateUserResponse.setChannelSessionId(text);
                            break;
                        }
                        if (tagname.equalsIgnoreCase("ChallengeQuestion")) {
                            // add employee object to list
                            authenticateUserResponse.setChallengeQuestion(text);
                            break;
                        }
                        if (tagname.equalsIgnoreCase("entry") && isChallenqeQuestionEntry) {

                            authenticateUserResponse.setChallengeQuestion(text);
                            isChallenqeQuestionEntry = false;
                            break;
                        }
                        if (tagname.equalsIgnoreCase("entry") && isChallenqeQuestionIdEntry) {
                            authenticateUserResponse.setChallengeQuestionId(text);
                            isChallenqeQuestionIdEntry = false;
                            break;
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
