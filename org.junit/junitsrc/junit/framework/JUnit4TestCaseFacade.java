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

	public List<Test> getTests() {
		// TODO Auto-generated method stub
		return null;
	}

	public void run(TestResult fTestResult, List<String> testNames) {
		// TODO Auto-generated method stub
		
	}
}