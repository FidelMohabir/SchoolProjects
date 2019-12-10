package edu.qc.seclass.glm;

import java.util.ArrayList;
import java.util.List;

public class ReminderType {

    private String nameOfType;
    private List<String> subTypes;


    public ReminderType(String name){
        this.nameOfType = name;
        subTypes = new ArrayList<String>();
    }


    public String getNameOfType() {
        return nameOfType;
    }

    /*public void setNameOfType(String nameOfType) {
        this.nameOfType = nameOfType;
    }*/

    public List<String> getSubTypes() {
        return subTypes;
    }

    public void addSubType(String subType) {
        subTypes.add(subType);
    }

}


