package edu.qc.seclass.replace;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({MainTest.class, MyMainTest.class, MainTestAddOn.class})

public class MainTestSuite {}

/*
This is a simple test suite that invokes all the test cases in test classes
MainTest , MyMainTest , and MainTestAddOn .
 */
