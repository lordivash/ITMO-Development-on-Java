package sample.bank;

import java.io.Serializable;

public class Account implements Serializable{
    private long accountNumber;
    private double amount;
    private AccountHistory history;

    public Account(){
        this.history = new AccountHistory();
    }
    public Account(double amount){
        this.amount = amount;
        this.history = new AccountHistory();

    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public AccountHistory getHistory(){
        return history;
    }

    public void setHistory(AccountHistory history){
        this.history = history;
    }

    public boolean withdrawAmount(double amount){
        if (this.amount >= amount) {
            this.amount -= amount;

            Record newRecord = new Record(ActionType.Withdrawal, this.amount + amount, this.amount);
            history.addRecord(newRecord);
            return true;
        }
        return false;
    }

    public void depositAmount(double amount){
        this.amount += amount;
        Record record = new Record(ActionType.Deposit, this.amount - amount, this.amount);
        history.addRecord(record);
    }

    public void transferAmount(Account target, double amount){
        if (this.amount >= amount){
            this.amount -= amount;
            Record record1 = new Record(ActionType.Transfer, this.amount + amount, this.amount);
            history.addRecord(record1);

            target.amount += amount;
            Record record2 = new Record(ActionType.Transfer, target.getAmount() - amount, target.getAmount());
            target.history.addRecord(record2);
        }
    }
}
