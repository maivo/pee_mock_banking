package pee.mockbanking.mb;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;
import com.x5.template.Chunk;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import pee.mockbanking.util.AssetUtils;

/**
 * Created by pvu_asus on 28/05/2015.
 */
public class MbClient {
    private static final String TAG = "MbClient";

    public static final String SECURITY_SERVICE_ENDPOINT ="https://www.atbmobile.com/mbanking/services/SecurityService/";

    public static String getSecurityServiceGetMultifactorSecurityInfoRequestXml(Context context, String userName, String password){
        String assetPath = "mb_securityService/securityService_getMultifactorSecurityInfo_request.cxml";
        String chunkTemplateContent = AssetUtils.getAssetContent(context, assetPath);

        //get result
        Chunk c = new Chunk();
        c.append(chunkTemplateContent);
        c.set("userName", userName);
        c.set("password", password);
        return c.toString();
    }

    public static String getSecurityServiceAuthenticateUserRequestXml(Context context, String userName, String password){
        String assetPath = "mb_securityService/securityService_authenticateUser_request.cxml";
        String chunkTemplateContent = AssetUtils.getAssetContent(context, assetPath);
        //get result
        Chunk c = new Chunk();
        c.append(chunkTemplateContent);
        c.set("userName", userName);
        c.set("password", password);
        return c.toString();
    }

    public static void post(Context context, String endPoint, String requestXml, ResponseHandlerInterface responseHandler){
        AsyncHttpClient client = new AsyncHttpClient();
        HttpEntity entity;
        try {
            entity = new StringEntity(requestXml);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String  contentType = "text/xml; charset=utf-8";


        client.addHeader("Content-Type", contentType);
        // client.addHeader("Accept", "text/xml");
        client.addHeader("SOAPAction", endPoint);
        client.post(context, endPoint, entity, contentType, responseHandler);

    }



}
