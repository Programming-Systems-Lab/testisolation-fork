package edu.washington.cs.dt.util;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

public class TestSampleTestsInFreshJVM extends TestCase {
	
	static final String className = "edu.washington.cs.dt.samples.TestShareGlobals";
	
	public void testAllPass() {
		
		String classPath = "";
		
		String outputFile = "./tests/edu/washington/cs/dt/samples/allpass_freshjvm.txt";
		
		List<String> tests = new LinkedList<String>();
		tests.add(className + ".test1");
		tests.add(className + ".test2");
		tests.add(className + ".test3");
		tests.add(className + ".test4");
		tests.add(className + ".test5");
		
		TestExecUtils.executeTestsInFreshJVM(classPath, outputFile, tests);
	}

	public void testFail() {
        String classPath = "";
		
		String outputFile = "./tests/edu/washington/cs/dt/samples/havefail_freshjvm.txt";
		
		List<String> tests = new LinkedList<String>();
		tests.add(className + ".test1");
		tests.add(className + ".test2");
		tests.add(className + ".test4");
		tests.add(className + ".test5");
		
		TestExecUtils.executeTestsInFreshJVM(classPath, outputFile, tests);
	}
	
	public void testException() {
        String classPath = "";
		
		String outputFile = "./tests/edu/washington/cs/dt/samples/haveerror_freshjvm.txt";
		
		List<String> tests = new LinkedList<String>();
		tests.add(className + ".test1");
		tests.add(className + ".test2");
		tests.add(className + ".test4");
		tests.add(className + ".test3");
		
		TestExecUtils.executeTestsInFreshJVM(classPath, outputFile, tests);
	}
	
}
