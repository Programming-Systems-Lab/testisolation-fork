/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.runners;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.columbia.cs.psl.polldet.FindPackages;
import edu.washington.cs.dt.OneTestExecResult;
import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.ProgressCallback;
import edu.washington.cs.dt.util.TestExecUtils;

public class PollDetRunner extends AbstractTestRunner {

	public PollDetRunner(List<String> tests) {
		super(tests);
	}
	
	public PollDetRunner(String fileName) {
		super(fileName);
	}
	
	@Override
	public TestExecResults run() {
		TestExecResults result = TestExecResults.createInstance();
		this.setNumberOfTests(super.junitTestList.size());
		this.testingStarted();
		

		for(String test : super.junitTestList) {
			Map<String, OneTestExecResult> singleRun = TestExecUtils.executeTestsInFreshJVMPollDet(super.getClassPath(),
					super.getTmpOutputFile(), Collections.singletonList(test), null);
			this.testCompleted();
			result.addExecutionResults(singleRun);
		}
		
		//check do we need to dump the fixed ordered results to an intermediate file
//		if(Main.fixedOrderReport != null) {
//			super.saveResultsToFile(singleRun, Main.fixedOrderReport);
//		}
		return result;
	}
}
