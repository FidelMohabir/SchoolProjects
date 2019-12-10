package edu.qc.seclass.glm;

import java.util.ArrayList;
import java.util.List;

public class ReminderList {

    private String nameOfList;
    private ArrayList<Reminder> remindersInList;

    public ReminderList(String name){
        this.nameOfList = name;
        this.remindersInList = new ArrayList<Reminder>();
    }

    public void addReminderToList(Reminder reminderToAdd){
        this.remindersInList.add(reminderToAdd);
    }

    public void reloadReminders(Database db){
            remindersInList = db.getRemindersByList(nameOfList);
    }

    public void editReminderInList(Reminder reminderToAdd){
        //TO BE IMPLEMENTED
    }

    public void deleteReminderFromList(Reminder reminderToAdd){
        //TO BE IMPLEMENTED
    }

    public void search(String reminderName){
        //TO BE IMPLEMENTED
    }

    public void uncheckAll(){
        for (Reminder r: remindersInList)
        {
            if(r.isChecked())
                r.setChecked(false);
        }
    }

    //Getters and Setters
    public String getNameOfList() {
        return nameOfList;
    }

    public void setNameOfList(String newListName){
        this.nameOfList = newListName;
    }

    public ArrayList<Reminder> getRemindersInList() {
        return remindersInList;
    }
    //Getters and Setters//
}
