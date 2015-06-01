package pee.mockbanking.util;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pvu_asus on 28/05/2015.
 */
public class AssetUtils {
    private static final String TAG = "SignUpActivity";

    public static String getAssetContent(Context context, String assetPath){
        Log.i(TAG, "inside getAssetContent");

        StringBuilder buf=new StringBuilder();
        BufferedReader in= null;
        String str;
        try {
            InputStream inputStream=context.getAssets().open(assetPath);

            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((str=in.readLine()) != null) {
                buf.append(str);
            }
            return buf.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
