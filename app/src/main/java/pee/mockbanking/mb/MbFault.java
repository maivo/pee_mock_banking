package pee.mockbanking.mb;

import android.app.Application;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class MbFault  {
    protected String message;
    protected String errorCode;
    protected String className;
    protected String dynamicContent;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDynamicContent() {
        return dynamicContent;
    }

    public void setDynamicContent(String dynamicContent) {
        this.dynamicContent = dynamicContent;
    }

    @Override
    public String toString() {
        return "MbFault{" +
                "message='" + message + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", className='" + className + '\'' +
                ", dynamicContent='" + dynamicContent + '\'' +
                '}';
    }


    /**
     * try to extract the mbFault object from an mbanking response
     * If response has no mbFault, return null
     *
     * eg response xml with a mbFault: rr_mbanking\login_03_invalid_username_password
     * @param bytes
     * @return
     */
    public static MbFault getMbFault(byte [] bytes){
        MbFault mbFault = null;
        MbFaultParser parser = new MbFaultParser();

        //Charset.forName("UTF-8")
        InputStream stream = new ByteArrayInputStream(bytes);
        mbFault= parser.parse(stream);
        return mbFault;
    }
}
