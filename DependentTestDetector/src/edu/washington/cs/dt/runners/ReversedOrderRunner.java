package edu.washington.cs.dt.runners;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.RESULT;
import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.util.TestExecUtils;

public class ReversedOrderRunner extends AbstractTestRunner {

	public ReversedOrderRunner(List<String> tests) {
		super(tests);
	}
	
	public ReversedOrderRunner(String fileName) {
		super(fileName);
	}

	@Override
	public TestExecResults run() {
		List<String> reversedTests = new LinkedList<String>();
		reversedTests.addAll(super.junitTestList);
		Collections.reverse(reversedTests);
		
		TestExecResults result = TestExecResults.createInstance();
        Map<String, RESULT> singleRun = TestExecUtils.executeTestsInFreshJVM(super.getClassPath(),
        		super.getTmpOutputFile(), reversedTests);
		result.addExecutionResults(singleRun);
		return result;
	}

}
