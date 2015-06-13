package pee.mockbanking.mb;

/**
 * Created by pvu_asus on 28/05/2015.
 */
public class MockMbEndPoints implements MbEndPointsI {
    private static final String TAG = "MbClient";

    private static final String BASE_URL ="http://172.20.10.4:8080/mbmock";
    private static MockMbEndPoints mbEndPoints = null;



    private void sleep(){
        try {
            //Thread.sleep(500);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getSsGetMultifactorSecurityInfo(){
        sleep();
        return BASE_URL +"/securityService/getMultifactorSecurityInfoResponse.xml";
    }

    public String getSsGetAuthenticateUser(){
        sleep();
        //return BASE_URL +"/securityService/authenticateUserResponse.xml";
        return BASE_URL +"/securityService/authenticateUserResponse_challenge.xml";
    }

    public String getAsGetAccounts(){
        return BASE_URL +"/accountService/getAccountsResponse.xml";
    }




}
