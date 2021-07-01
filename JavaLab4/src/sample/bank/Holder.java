package sample.bank;

import java.io.Serializable;

public class Holder implements Serializable {
    private long holderId;
    private String fullName;
    private Account account;

    public Holder(String fullName){
        this.fullName = fullName;
        openAccount();
    }

    public void openAccount(){
        this.account = new Account();
    }

    public void openAccount(double amount){
        this.account = new Account(amount);
    }

    public void closeAccount(){
        this.account = null;
    }

    public long getHolderId(){
        return holderId;
    }

    public void setHolderId(long holderId) {
        this.holderId = holderId;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setAccount(Account account){
        this.account = account;
    }

    public Account getAccount(){
        return account;
    }
}
