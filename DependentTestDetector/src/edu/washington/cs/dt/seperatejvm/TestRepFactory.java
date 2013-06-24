package edu.washington.cs.dt.seperatejvm;

import java.util.HashMap;
import java.util.List;

import edu.washington.cs.dt.abstractions.TestRep;
import edu.washington.cs.dt.tools.UnitTestFinder;

public class TestRepFactory {
	
	HashMap<String,List<TestRep>> suites = new HashMap<>();

	public TestRep getTest(String methodName, int number) {
		if (!suites.containsKey(methodName)) {
			loadSuite(methodName);
		}
		return suites.get(methodName).get(number-1);
	}

	private void loadSuite(String methodName) {
		List<TestRep> suite = new UnitTestFinder().getUnitTestsFromJunit3TestSuiteMethod(methodName);
		suites.put(methodName, suite);
	}
	
}
