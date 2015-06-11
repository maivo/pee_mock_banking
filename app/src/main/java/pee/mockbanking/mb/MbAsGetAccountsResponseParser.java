package pee.mockbanking.mb;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pvu_asus on 03/06/2015.
 */
public class MbAsGetAccountsResponseParser {

    private static final String TAG = "MbAsGetAccounts.P";
    List<Account> accountList;

    private String text;

    public MbAsGetAccountsResponseParser() {
        accountList = new ArrayList<Account>();
    }



    public  List<Account> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();

            Account account= null;

            String entryKeyValue = null;
            boolean isDesc = false;
            boolean isAvailBalFormatted = false;
            boolean isBalanceFormatted = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("return")) {
                            account = new Account();
                        }
                        if (tagname.equalsIgnoreCase("entry")) {
                            isBalanceFormatted = false;
                            isDesc = false;
                            isAvailBalFormatted =false;
                            entryKeyValue = parser.getAttributeValue(null, "key");
                            if("Desc".equalsIgnoreCase(entryKeyValue)){
                                isDesc = true;
                            }
                            if("AvailBalFormatted".equalsIgnoreCase(entryKeyValue)){
                                isAvailBalFormatted = true;
                            }
                            if("BalanceFormatted".equalsIgnoreCase(entryKeyValue)){
                                isBalanceFormatted = true;
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("return")) {
                            accountList.add(account);
                            break;
                        }
                        if (tagname.equalsIgnoreCase("accountId")) {
                            account.setAccountId(text);
                            break;
                        }

                        if (tagname.equalsIgnoreCase("currencyCode")) {
                            account.setCurrencyCode(text);
                            break;
                        }
                        if (tagname.equalsIgnoreCase("status")) {
                            account.setStatus(text);
                            break;
                        }
                        if (tagname.equalsIgnoreCase("nickName")) {
                            account.setNickName(text);
                            break;
                        }
                        if (tagname.equalsIgnoreCase("type")) {
                            account.setType(text);
                            break;
                        }

                        if (tagname.equalsIgnoreCase("entry") && isDesc) {
                            account.setDesc(text);
                            break;
                        }
                        if (tagname.equalsIgnoreCase("entry") && isAvailBalFormatted) {
                            account.setAvailBalFormatted(text);
                            break;
                        }
                        if (tagname.equalsIgnoreCase("entry") && isBalanceFormatted) {
                            account.setBalanceFormatted(text);
                            break;
                        }

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

        return accountList;
    }
}
