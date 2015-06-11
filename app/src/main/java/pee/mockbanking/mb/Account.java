package pee.mockbanking.mb;

import android.app.Application;

import java.util.List;

/**
 * Created by pvu_asus on 25/05/2015.
 */
public class Account extends Application {
    String accountId;
    String type;
    String currencyCode;
    String status;
    String nickName;

    String desc;
    String availBalFormatted;
    String balanceFormatted;


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAvailBalFormatted() {
        return availBalFormatted;
    }

    public void setAvailBalFormatted(String availBalFormatted) {
        this.availBalFormatted = availBalFormatted;
    }

    public String getBalanceFormatted() {
        return balanceFormatted;
    }

    public void setBalanceFormatted(String balanceFormatted) {
        this.balanceFormatted = balanceFormatted;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", type='" + type + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", status='" + status + '\'' +
                ", nickName='" + nickName + '\'' +
                ", desc='" + desc + '\'' +
                ", availBalFormatted='" + availBalFormatted + '\'' +
                ", balanceFormatted='" + balanceFormatted + '\'' +
                '}';
    }

    public static String toString(List<Account> accountList) {
        StringBuilder builder = new StringBuilder();
        builder.append("accountList.size: "+accountList.size());
        builder.append("\n");
        for(Account account: accountList){
            builder.append(account);

        }
        return builder.toString();
    }
}
