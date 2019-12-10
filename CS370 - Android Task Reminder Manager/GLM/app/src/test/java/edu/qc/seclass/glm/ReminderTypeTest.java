package edu.qc.seclass.glm;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReminderTypeTest {
    ReminderType type = new ReminderType("type name");
    @Test
    public void getNameOfType() {
        assertEquals("type name",type.getNameOfType());
    }

    @Test
    public void getSubTypes() {
        type.addSubType("sub type");
        type.addSubType("sub type2");
        assertEquals(2,type.getSubTypes().size());
    }

    @Test
    public void addSubType() {
        type.addSubType("sub type");
        assertEquals(1,type.getSubTypes().size());
    }
}