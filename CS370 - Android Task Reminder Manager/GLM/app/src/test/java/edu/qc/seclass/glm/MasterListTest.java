package edu.qc.seclass.glm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import static org.junit.Assert.*;

public class MasterListTest {

    @Before
    public void setup(){

    }

    MasterList master;
    @Test
    public void addList1() {
        master = MasterList.getInstance();
        master.addList("list1");
        assertEquals(1,master.getAllLists().size());
    }

    @Test
    public void addList2() {
        master = MasterList.getInstance();
        master.addList("list1");
        assertNotEquals(1,master.getAllLists().size());
    }

    @Test
    public void getAllLists() {
        master = MasterList.getInstance();
        master.addList("list2");
        assertEquals(3,master.getAllLists().size());
    }

    @Test
    public void addType1() {
        master = MasterList.getInstance();
        master.addType("list type1");
        assertEquals(1,master.getAllTypes().size());
    }

    @Test
    public void addType2() {
        master = MasterList.getInstance();
        master.addType("list type1");
        assertNotEquals(1,master.getAllTypes().size());
    }

    @Test
    public void getAllTypes() {
        master = MasterList.getInstance();
        master.addType("list type2");
        master.addType("list type3");
        assertEquals(4,master.getAllTypes().size());
    }
}