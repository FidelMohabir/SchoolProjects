package edu.qc.seclass.glm;

import java.util.Date;

public class Alert {

    public void setAlertID(long alertID) {
        this.alertID = alertID;
    }

    private long alertID;
    private int reminderID;
    private boolean isActive;
    private long day;
    private boolean repeatDaily = false;
    private boolean repeatWeekly = false;
    private boolean repeatMonthly = false;
    private boolean repeatYearly = false;

    public Alert(){

    }

    public Alert (long dayOfAlert){
        this.day = dayOfAlert;
        this.isActive = true;
    }

    public Alert (long dayOfAlert, String repeat){
        this.day = dayOfAlert;
        this.isActive = true;
        switch (repeat){
            case "Daily":
                repeatDaily = true;
            case "Weekly":
                repeatWeekly = true;
            case "Monthly":
                repeatMonthly = true;
            case "Yearly":
                repeatYearly = true;
        }
    }

    public void toggleAlert(){
        if (isActive)
            isActive = false;
        else isActive = true;
    }

    public void editAlert(Alert newAlert){
        this.isActive = newAlert.isActive;
        this.day = newAlert.day;
        this.repeatDaily = newAlert.repeatDaily;
        this.repeatWeekly = newAlert.repeatWeekly;
        this.repeatMonthly = newAlert.repeatMonthly;
        this.repeatYearly = newAlert.repeatYearly;
    }

    //Getters and Setters
	public long getAlertID(){
		return alertID;
	}
	
    public boolean getAlertStatus() {
        return isActive;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public boolean isRepeatDaily() {
        return repeatDaily;
    }

    public void setRepeatDaily(boolean repeatDaily) {
        this.repeatDaily = repeatDaily;
    }

    public boolean isRepeatWeekly() {
        return repeatWeekly;
    }

    public void setRepeatWeekly(boolean repeatWeekly) {
        this.repeatWeekly = repeatWeekly;
    }

    public boolean isRepeatMonthly() {
        return repeatMonthly;
    }

    public void setRepeatMonthly(boolean repeatMonthly) {
        this.repeatMonthly = repeatMonthly;
    }

    public boolean isRepeatYearly() {
        return repeatYearly;
    }

    public void setRepeatYearly(boolean repeatYearly) {
        this.repeatYearly = repeatYearly;
    }
    //Getters and Setters//
}
