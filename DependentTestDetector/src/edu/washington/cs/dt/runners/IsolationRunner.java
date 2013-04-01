/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.runners;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.OneTestExecResult;
import edu.washington.cs.dt.TestExecResult;
import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.TestExecUtils;

public class IsolationRunner extends AbstractTestRunner {
	
	private float executionTime = 0.0f;

	public IsolationRunner(List<String> tests) {
		super(tests);
	}
	
	public IsolationRunner(String fileName) {
		super(fileName);
	}
	
	@Override
	public TestExecResults run() {
		TestExecResults result = TestExecResults.createInstance();
		this.setNumberOfTests(this.junitTestList.size());
		this.testingStarted();
		for(String test : super.junitTestList) {
			Map<String, OneTestExecResult> singleRun = TestExecUtils.executeTestsInFreshJVM(super.getClassPath(),
					super.getTmpOutputFile(), Collections.singletonList(test), null);
			this.testCompleted();
			result.addExecutionResults(singleRun);
		}
		
		
		//dump an intermediate results
		if(Main.isolationReport != null) {
			Collection<Map<String, OneTestExecResult>> results = new LinkedList<Map<String, OneTestExecResult>>();
			for(TestExecResult r : result.getExecutionRecords()) {
				results.add(r.singleRun);
			}
			super.saveResultsToFile(results, Main.isolationReport);
		}
		
		return result;
	}
}