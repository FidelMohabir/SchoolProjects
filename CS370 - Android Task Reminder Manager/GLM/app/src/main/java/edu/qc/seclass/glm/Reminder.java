package edu.qc.seclass.glm;

public class Reminder {

    public int reminderID;
    public String reminderList;
    public String reminderType;
    public String reminderName;
    private boolean checked = false;
    private Alert alert;

    public Reminder(String reminderList, String reminderType, String reminderName) {
        this.reminderList = reminderList;
        this.reminderType = reminderType;
        this.reminderName = reminderName;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public boolean hasAlert(){
        if (alert==null) return false;
        return true;
    }

    public void toggleCheck(){
        this.checked = !isChecked();
    }

    public void addAlert(Alert alertToAdd){
        //this.alert = new Alert(alert.getDay());

        if (alertToAdd.isRepeatDaily())
            this.alert = new Alert(alertToAdd.getDay(), "Daily");
        else if (alertToAdd.isRepeatWeekly())
            this.alert = new Alert(alertToAdd.getDay(), "Weekly");
        else if (alertToAdd.isRepeatMonthly())
            this.alert = new Alert(alertToAdd.getDay(), "Monthly");
        else if (alertToAdd.isRepeatYearly())
            this.alert = new Alert(alertToAdd.getDay(), "Yearly");
        else this.alert = new Alert(alertToAdd.getDay());
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean setCheck) {
        checked = setCheck;
    }

}
