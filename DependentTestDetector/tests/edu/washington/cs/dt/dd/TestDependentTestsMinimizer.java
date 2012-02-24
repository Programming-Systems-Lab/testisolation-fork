package edu.washington.cs.dt.dd;

import java.util.LinkedList;
import java.util.List;

import edu.washington.cs.dt.util.RESULT;

import junit.framework.TestCase;

public class TestDependentTestsMinimizer extends TestCase {

	public void test1() {
		List<String> tests = new LinkedList<String>();
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test1");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test2");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testDummy");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test4");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testDummy1");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testDummy2");
		
		
		String dependentTest = "edu.washington.cs.dt.samples.TestShareGlobals.test5";
		RESULT intendedResult = RESULT.FAILURE;
		String classPath = "";
		String tmpOutputFile = "./tests/edu/washington/cs/dt/dd/tmpFile1.txt";
		
		DependentTestSetMinimizer minimizer = new DependentTestSetMinimizer(tests, dependentTest,
				intendedResult, classPath, tmpOutputFile);
		List<String> minTests = minimizer.minimize();
		System.out.println(minTests);
	}
	
	public void test2() {
		List<String> tests = new LinkedList<String>();
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr1");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr2");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr3");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr4");
		
		
		String dependentTest = "edu.washington.cs.dt.samples.TestShareGlobals.testStr5";
		RESULT intendedResult = RESULT.PASS;
		String classPath = "";
		String tmpOutputFile = "./tests/edu/washington/cs/dt/dd/tmpFile2.txt";
		
		DependentTestSetMinimizer minimizer = new DependentTestSetMinimizer(tests, dependentTest,
				intendedResult, classPath, tmpOutputFile);
		List<String> minTests = minimizer.minimize();
		System.out.println(minTests);
	}
	
}
