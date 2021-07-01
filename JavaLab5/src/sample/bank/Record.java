package sample.bank;

import java.util.Date;

public class Record {
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

    public Record(ActionType actionType, double beforeAction, double afterAction, Date actionDate){
        this.actionType = actionType;
        this.beforeAction = beforeAction;
        this.afterAction = afterAction;
        this.actionDate = actionDate;
    }

    @Override
    public String toString(){
        return actionType + " | " + beforeAction + " -> " + afterAction + " | " + actionDate.toString();
    }
}

