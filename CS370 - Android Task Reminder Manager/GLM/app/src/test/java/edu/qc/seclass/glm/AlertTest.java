package edu.qc.seclass.glm;

import org.junit.Test;

import static org.junit.Assert.*;

public class AlertTest {
    Alert alert = new Alert();
    @Test
    public void setAlertID() {

    }

    @Test
    public void toggleAlert() {
        alert.toggleAlert();
        assertEquals(true,alert.getAlertStatus());
    }

    @Test //toggle back
    public void toggleAlert2() {
        alert.toggleAlert();
        alert.toggleAlert();
        assertEquals(false,alert.getAlertStatus());
    }

    @Test //edit alert for day
    public void editAlert() {
        Alert newAlert = new Alert(123,"weekly");
        alert.editAlert(newAlert);
        assertEquals(123,alert.getDay());
    }

    @Test //edit alert for status
    public void editAlert2() {
        Alert newAlert = new Alert(123,"weekly");
        newAlert.toggleAlert();
        alert.editAlert(newAlert);
        assertEquals(false,alert.getAlertStatus());
    }

    @Test //edit alert for day
    public void editAlert3() {
        Alert newAlert = new Alert(123,"weekly");
        newAlert.toggleAlert();
        alert.editAlert(newAlert);
        assertEquals(false,alert.isRepeatWeekly());
    }

    @Test
    public void getAlertID() {
        alert.setAlertID(123);
        assertEquals(123,alert.getAlertID());
    }

    @Test
    public void getAlertStatus() {
        assertEquals(false,alert.getAlertStatus());
    }

    @Test
    public void getDay() {
        Alert newAlert = new Alert(123,"weekly");
        alert.editAlert(newAlert);
        assertEquals(123,alert.getDay());
    }

    @Test
    public void setDay() {
        alert.setDay(12);
        assertEquals(12,alert.getDay());
    }

    @Test
    public void isRepeatDaily() {
        alert.setRepeatDaily(true);
        assertEquals(true,alert.isRepeatDaily());
    }

    @Test
    public void setRepeatDaily() {
        alert.setRepeatDaily(false);
        assertEquals(false,alert.isRepeatDaily());
    }


    @Test
    public void isRepeatWeekly() {
        alert.setRepeatWeekly(true);
        assertEquals(true,alert.isRepeatWeekly());
    }

    @Test
    public void setRepeatWeekly() {
        alert.setRepeatWeekly(false);
        assertEquals(false,alert.isRepeatWeekly());
    }

    @Test
    public void isRepeatMonthly() {
        alert.setRepeatMonthly(true);
        assertEquals(true,alert.isRepeatMonthly());
    }

    @Test
    public void setRepeatMonthly() {
        alert.setRepeatMonthly(false);
        assertEquals(false,alert.isRepeatMonthly());
    }

    @Test
    public void isRepeatYearly() {
        alert.setRepeatYearly(true);
        assertEquals(true,alert.isRepeatYearly());
    }

    @Test
    public void setRepeatYearly() {
        alert.setRepeatYearly(false);
        assertEquals(false,alert.isRepeatYearly());
    }
}