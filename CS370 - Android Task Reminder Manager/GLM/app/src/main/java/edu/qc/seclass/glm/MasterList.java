package edu.qc.seclass.glm;

import java.util.ArrayList;
import java.util.List;

public class MasterList {

    private static MasterList instance;

    private ArrayList<ReminderList> allLists;
    private ArrayList<ReminderType> allTypes;
    private ArrayList<String> allTypeNames;

    private MasterList(){
        allLists = new ArrayList<ReminderList>();
        allTypes = new ArrayList<ReminderType>();
        allTypeNames = new ArrayList<String>();
        for (ReminderType type: allTypes) {
            allTypeNames.add(type.getNameOfType());
        }
    }

    public static synchronized MasterList getInstance(){
        if(instance==null){
            instance=new MasterList();
        }
        return instance;
    }

    //Getters and Setters
    public ArrayList<ReminderList> getAllLists() {
        return allLists;
    }

    public ArrayList<ReminderType> getAllTypes() {
        return allTypes;
    }

    public ArrayList<String> getAllTypeNames() {
        return allTypeNames;
    }
    //Getters and Setters//

    public void addList(String name) {
        ReminderList listToAdd = new ReminderList(name);
        this.allLists.add(listToAdd);
    }

    public void addType(String name) {
        ReminderType typeToAdd = new ReminderType(name);
        this.allTypes.add(typeToAdd);
    }

    public void addType2(String name) {
        this.allTypeNames.add(name);
    }

    public void renameList(String oldName, String newName){
        //TO BE IMPLEMENTED
    }

    public void deleteList(String name){
        //TO BE IMPLEMENTED
    }
}
