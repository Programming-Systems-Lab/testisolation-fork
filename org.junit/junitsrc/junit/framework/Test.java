package junit.framework;

import java.util.List;

/**
 * A <em>Test</em> can be run and collect its results.
 *
 * @see TestResult
 */
public interface Test {
	/**
	 * Counts the number of test cases that will be run by this test.
	 */
	public abstract int countTestCases();
	/**
	 * Runs a test and collects its result in a TestResult instance.
	 */
	public abstract void run(TestResult result);
	
	/**
	 * WL
	 * @return a list of tests
	 */
	public abstract List<Test> getTests();
	
	/** 
	 * WL
	 * @param fTestResult
	 * @param testNames
	 * 
	 * used to specify which tests to run
	 */
	public abstract void run(TestResult fTestResult, List<String> testNames);
}