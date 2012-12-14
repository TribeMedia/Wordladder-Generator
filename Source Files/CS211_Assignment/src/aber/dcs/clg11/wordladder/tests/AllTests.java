package aber.dcs.clg11.wordladder.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite used to execute all JUnit tests.
 * 
 * @author Connor Goddard (clg11)
 */
@RunWith(Suite.class)
@SuiteClasses({ GraphTest.class, NodeTest.class, OutputStackTest.class,
		ReadFileTest.class })
public class AllTests {

}
