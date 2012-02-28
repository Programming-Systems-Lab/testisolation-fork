package edu.washington.cs.dt.runners;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.OneTestExecResult;
import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.util.TestExecUtils;

public class IsolationRunner extends AbstractTestRunner {

	public IsolationRunner(List<String> tests) {
		super(tests);
	}
	
	public IsolationRunner(String fileName) {
		super(fileName);
	}
	
	@Override
	public TestExecResults run() {
		TestExecResults result = TestExecResults.createInstance();
		
		for(String test : super.junitTestList) {
			Map<String, OneTestExecResult> singleRun = TestExecUtils.executeTestsInFreshJVM(super.getClassPath(),
					super.getTmpOutputFile(), Collections.singletonList(test));
			result.addExecutionResults(singleRun);
		}
		
		return result;
	}
}