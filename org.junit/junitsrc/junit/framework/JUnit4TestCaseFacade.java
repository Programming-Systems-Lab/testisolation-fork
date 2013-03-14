/**
 * 
 */
package junit.framework;

import java.util.List;

import org.junit.runner.Describable;
import org.junit.runner.Description;

public class JUnit4TestCaseFacade implements Test, Describable {
	private final Description fDescription;

	JUnit4TestCaseFacade(Description description) {
		fDescription = description;
	}

	@Override
	public String toString() {
		return getDescription().toString();
	}

	public int countTestCases() {
		return 1;
	}

	public void run(TestResult result) {
		throw new RuntimeException(
				"This test stub created only for informational purposes.");
	}

	public Description getDescription() {
		return fDescription;
	}

	// WL added to support passing in generating a list of tests
	public List<Test> getTests() {
		System.err.println("unsupported call to junit.framework.JUnit4TestCaseFacade for getting list of tests");
		return null;
	}

	// WL added to support passing in a list of tests
	public void run(TestResult fTestResult, List<String> testNames) {
		System.err.println("unsupported call to junit.framework.JUnit4TestCaseFacade for running list of tests");
	}
}