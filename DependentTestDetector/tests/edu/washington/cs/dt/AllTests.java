package edu.washington.cs.dt;

import edu.washington.cs.dt.dd.TesSimplifyDD;
import edu.washington.cs.dt.dd.TestDDMin;
import edu.washington.cs.dt.dd.TestDependentTestsMinimizer;
import edu.washington.cs.dt.runners.TestCombinatorialRunner;
import edu.washington.cs.dt.runners.TestFixedOrderRunner;
import edu.washington.cs.dt.runners.TestIsolationRunner;
import edu.washington.cs.dt.util.TestPermutationGenerator;
import edu.washington.cs.dt.util.TestSampleTestsInFreshJVM;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		
		suite.addTest(TestDependentTestDetector.suite());
		suite.addTest(TestExecutionResultsDifferentior.suite());
		
		suite.addTest(TesSimplifyDD.suite());
		suite.addTest(TestDDMin.suite());
		suite.addTest(TestDependentTestsMinimizer.suite());
		
		suite.addTest(TestCombinatorialRunner.suite());
		suite.addTest(TestFixedOrderRunner.suite());
		suite.addTest(TestIsolationRunner.suite());
		
		suite.addTest(TestPermutationGenerator.suite());
		suite.addTest(TestSampleTestsInFreshJVM.suite());
		
		return suite;
	}
	
}
