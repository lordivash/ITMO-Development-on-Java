package sample.bank;

import java.util.ArrayList;

public class AccountHistory {
    private ArrayList<Record> records;

    public AccountHistory(){
        records = new ArrayList<Record>();
    }

    public AccountHistory(ArrayList<Record> records){
        this.records = records;
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public ArrayList<Record> getRecords(){
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }
}
