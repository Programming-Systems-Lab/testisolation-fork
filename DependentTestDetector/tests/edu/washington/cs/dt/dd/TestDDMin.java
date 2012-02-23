package edu.washington.cs.dt.dd;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestDDMin extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(TestMultiInterferenceFaultMinimizer.suite());
		suite.addTest(TestMultiNonInterferenceFaultMinimizer.suite());
		suite.addTest(TestOneFaultMinimizer.suite());
		
		return suite;
	}
	
}
