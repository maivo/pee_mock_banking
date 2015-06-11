package pee.mockbanking.mb;

/**
 * Created by pvu_asus on 28/05/2015.
 */
public class MbEndPoints implements MbEndPointsI {
    private static final String TAG = "MbClient";

    private static final String SECURITY_SERVICE ="https://www.atbmobile.com/mbanking/services/SecurityService";
    private static final String ACCOUNT_SERVICE ="https://www.atbmobile.com/mbanking/services/AccountService";
    private static MbEndPointsI mbEndPoints = null;


    public static MbEndPointsI getInstance(){
        if(mbEndPoints != null){
            return mbEndPoints;
        }
        //mbEndPoints = new MbEndPoints();
        mbEndPoints = new MockMbEndPoints();
        return mbEndPoints;
    }


    public String getSsGetMultifactorSecurityInfo(){
        return SECURITY_SERVICE;
    }

    public String getSsGetAuthenticateUser(){
        return SECURITY_SERVICE;
    }

    public String getAsGetAccounts(){
        return ACCOUNT_SERVICE;
    }

}
