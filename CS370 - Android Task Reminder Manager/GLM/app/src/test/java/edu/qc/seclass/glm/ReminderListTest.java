package edu.qc.seclass.glm;

import android.app.Activity;
import android.content.Context;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReminderListTest {

    MasterList master = MasterList.getInstance();
    ReminderList reminderList = new ReminderList("list name");

    Reminder reminder = new Reminder("reminderList","reminderType","reminderName");
    Reminder reminder2 = new Reminder("reminderList2","reminderType2","reminderName2");
    @Test //able to add a reminder to list
    public void addReminderToList1() {
        reminderList.addReminderToList(reminder);
        assertEquals(1,reminderList.getRemindersInList().size());
    }

    @Test //should able to add multiple reminder to a list
    public void addReminderToList2() {
        reminderList.addReminderToList(reminder);
        reminderList.addReminderToList(reminder2);
        assertEquals(2,reminderList.getRemindersInList().size());
    }

    @Test
    public void editReminderInList() {


    }

    @Test
    public void deleteReminderFromList() {
    }

    @Test
    public void search() {
    }

    @Test //able to uncheck one reminder
    public void uncheckAll1() {
        reminderList.addReminderToList(reminder);
        reminder.toggleCheck();
        reminderList.uncheckAll();
        assertEquals(false,reminder.isChecked());
    }
    @Test //able to uncheck two reminder
    public void uncheckAll2() {
        reminderList.addReminderToList(reminder);
        reminderList.addReminderToList(reminder2);
        reminder.toggleCheck();
        reminder2.toggleCheck();
        reminderList.uncheckAll();
        assertEquals(false,reminder.isChecked());
        assertEquals(false,reminder2.isChecked());
    }

    @Test //able to uncheck 1 checked and another 1 unchecked
    public void uncheckAll3() {
        reminderList.addReminderToList(reminder);
        reminderList.addReminderToList(reminder2);
        reminder.toggleCheck();
        reminderList.uncheckAll();
        assertEquals(false,reminder.isChecked());
        assertEquals(false,reminder2.isChecked());
    }
    @Test
    public void getNameOfList() {
        assertEquals("list name",reminderList.getNameOfList());
    }

    @Test
    public void setNameOfList() {
        reminderList.setNameOfList("new list name");
        assertEquals("new list name",reminderList.getNameOfList());
    }

    @Test
    public void getRemindersInList() {
        reminderList.addReminderToList(reminder);
        reminderList.addReminderToList(reminder2);
        assertEquals(2,reminderList.getRemindersInList().size());
    }
}