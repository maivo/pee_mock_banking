package pee.mockbanking.mb;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Try to extract the mbFault object from a mbanking response xml
 * If response has no mbFault, return null
 *
 * Looks like a mbFault will be contained in a responseXml from a loopj onFailure.
 * void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable)
 *
 * eg response xml with a mbFault: rr_mbanking\login_03_invalid_username_password
 */
public class MbFaultParser {

    private static final String TAG = "MbFaultParser";

    String text = null;
    public MbFault parse(InputStream is) {
        MbFault mbFault = null;
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

                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("mbankingFault")) {
                            mbFault = new MbFault();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("message")) {
                            mbFault.setMessage(text);
                        }else  if (tagname.equalsIgnoreCase("errorCode")) {
                            mbFault.setErrorCode(text);
                        }else  if (tagname.equalsIgnoreCase("className")) {
                            mbFault.setClassName(text);
                        }else  if (tagname.equalsIgnoreCase("dynamicContent")) {
                            mbFault.setDynamicContent(text);
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

        return mbFault;
    }
}
