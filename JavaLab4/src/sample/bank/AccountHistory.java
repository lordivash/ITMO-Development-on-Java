package sample.bank;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountHistory implements Serializable{
    private ArrayList<Record> records;

    public AccountHistory(){
        records = new ArrayList<Record>();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public ArrayList<Record> getRecords(){
        return records;
    }
}
