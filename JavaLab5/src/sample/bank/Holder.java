package sample.bank;

public class Holder {
    private long holderId;
    private String fullName;
    private Account account;

    public Holder(String fullName){
        this.fullName = fullName;
        this.account = new Account();
    }

    public Holder(long holderId, String fullName, Account account){
        this.holderId = holderId;
        this.fullName = fullName;
        this.account = account;
    }

    public Holder(long holderId, String fullName){
        this.holderId = holderId;
        this.fullName = fullName;
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
