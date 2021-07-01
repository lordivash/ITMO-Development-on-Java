package sample.bank;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {
    ActionType actionType;

    double beforeAction;
    double afterAction;

    Date actionDate;

    public Record(ActionType actionType, double beforeAction, double afterAction){
        this.actionType = actionType;
        this.beforeAction = beforeAction;
        this.afterAction = afterAction;
        actionDate = new Date();
    }

    @Override
    public String toString(){
        return String.valueOf(actionType) + " | " + beforeAction + " -> " + afterAction + " | " + actionDate.toString();
    }
}

