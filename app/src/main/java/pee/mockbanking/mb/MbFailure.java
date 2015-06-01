package pee.mockbanking.mb;

import android.app.Application;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class MbFailure  {

    byte[] bytes;
    Throwable throwable;
    MbFault mbFault;

    public MbFailure(byte[] bytes, Throwable throwable){
        this.bytes = bytes;
        this.throwable = throwable;

        //
        if(bytes == null){
            return;
        }

        //
        mbFault = MbFault.getMbFault(bytes);
    }

    public String getDetailMsg(){
        if(mbFault != null){
            return mbFault.getDynamicContent();
        }
        return throwable.getMessage();
    }
}
