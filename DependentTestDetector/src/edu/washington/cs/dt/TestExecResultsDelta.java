package edu.washington.cs.dt;

import java.util.LinkedList;
import java.util.List;

import edu.washington.cs.dt.util.Globals;
import edu.washington.cs.dt.util.RESULT;

class TestExecResultsDelta {
	public final String testName;
	public final RESULT intendedResult;
	public final List<String> intendedPreTests;
	public final RESULT divergentResult;
	public final List<String> dependentTests; /*the tests executed before testName */
	
	public TestExecResultsDelta(String testName, RESULT intendedResult,
			List<String> intendedPreTests, RESULT divergentResult, List<String> dependentTests) {
		this.testName = testName;
		this.intendedResult = intendedResult;
		this.intendedPreTests = new LinkedList<String>();
		this.intendedPreTests.addAll(intendedPreTests);
		this.divergentResult = divergentResult;
		this.dependentTests = new LinkedList<String>();
		this.dependentTests.addAll(dependentTests);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Test: " + testName);
		sb.append(Globals.lineSep);
		sb.append("Intended behavior: " + intendedResult);
		sb.append(Globals.lineSep);
		sb.append("when executed after: " + intendedPreTests.toString());
		sb.append(Globals.lineSep);
		sb.append("The revealed different behavior: " + divergentResult);
		sb.append(Globals.lineSep);
		sb.append("when executed after: " + dependentTests.toString());
		sb.append(Globals.lineSep);
		return sb.toString();
	}
}