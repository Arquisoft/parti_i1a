package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import producer.GeneratorTest;

@RunWith(Suite.class)
@SuiteClasses({hello.MainControllerTest.class,
    	       persistence.DatabaseTest.class,
    	       GeneratorTest.class})
public class AllTests {

}
