package junit.extensions;

import java.util.List;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestResult;

/**
 * A Decorator for Tests. Use TestDecorator as the base class for defining new
 * test decorators. Test decorator subclasses can be introduced to add behaviour
 * before or after a test is run.
 * 
 */
public class TestDecorator extends Assert implements Test {
	protected Test fTest;

	public TestDecorator(Test test) {
		fTest= test;
	}

	/**
	 * The basic run behaviour.
	 */
	public void basicRun(TestResult result) {
		fTest.run(result);
	}

	public int countTestCases() {
		return fTest.countTestCases();
	}

	public void run(TestResult result) {
		basicRun(result);
	}

	@Override
	public String toString() {
		return fTest.toString();
	}

	public Test getTest() {
		return fTest;
	}

	// WL added to support passing in generating a list of tests
	public List<Test> getTests() {
		System.err.println("unsupported call to junit.extensions.TestDecorator for getting list of tests");
		return null;
	}

	// WL added to support passing in a list of tests
	public void run(TestResult fTestResult, List<String> testNames) {
		System.err.println("unsupported call to junit.extensions.TestDecorator for running list of tests");
	}
}