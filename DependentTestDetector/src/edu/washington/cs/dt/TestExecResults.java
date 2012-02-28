package edu.washington.cs.dt;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.util.Globals;

public class TestExecResults {
	
	/*each item in the list represents one launch of JVM*/
	private List<TestExecResult> executionRecords
	    = new LinkedList<TestExecResult>();
	
	private TestExecResults() {}
	
	public static TestExecResults createInstance() {
		return new TestExecResults();
	}
	
	/*add the result from one JVM run*/
	public void addExecutionResults(Map<String, OneTestExecResult> result) {
		TestExecResult r = new TestExecResult(result);
		this.executionRecords.add(r);
	}
	
	public List<TestExecResult> getExecutionRecords() {
		return this.executionRecords;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for(TestExecResult r : executionRecords) {
			sb.append((count++) + "-th run");
			sb.append(Globals.lineSep);
			sb.append("Pass: " + r.passingTestsInOrder.size() +
					", Fail: " + r.failingTestsInOrder.size() + ", Error: " + r.errorTestsInOrder.size());
			sb.append(Globals.lineSep);
			sb.append(r.toString());
			sb.append(Globals.lineSep);
		}
		return sb.toString();
	}
}

/**
 * TODO should add an exception stack trace here
 * to see the exactly error has been thrown
 * */
class TestExecResult {
	public final Map<String, OneTestExecResult> singleRun;

	protected List<String> allTests = new LinkedList<String>();
	
	/* The classified result after running all tests */
	protected List<String> passingTestsInOrder = new LinkedList<String>();
	protected List<String> failingTestsInOrder = new LinkedList<String>();
	protected List<String> errorTestsInOrder = new LinkedList<String>();
	
	public TestExecResult(Map<String, OneTestExecResult> singleRun) {
		this.singleRun = singleRun;
		//classify each test
		for(String test : this.singleRun.keySet()) {
			allTests.add(test);
			//check the results
			OneTestExecResult r = this.singleRun.get(test);
			if(r.result.equals(RESULT.PASS)) {
				this.passingTestsInOrder.add(test);
			} else if (r.result.equals(RESULT.FAILURE)) {
				this.failingTestsInOrder.add(test);
			} else if (r.result.equals(RESULT.ERROR)) {
				this.errorTestsInOrder.add(test);
			} else {
				throw new RuntimeException("Unknown results: " + r);
			}
		}
	}
	
	public List<String> getAllTests() {
		return this.allTests;
	}
	
	public OneTestExecResult getResult(String test) {
		return this.singleRun.get(test);
	}
	
	public boolean isTestPassed(String test) {
		return this.passingTestsInOrder.contains(test);
	}
	
	public boolean isTestFailed(String test) {
		return this.failingTestsInOrder.contains(test);
	}
	
	public boolean isTestError(String test) {
		return this.errorTestsInOrder.contains(test);
	}
	
	@Override
	public String toString() {
		return this.singleRun.toString();
	}
}