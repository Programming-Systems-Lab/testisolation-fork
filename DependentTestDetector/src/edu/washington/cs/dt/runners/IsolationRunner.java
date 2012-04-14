package edu.washington.cs.dt.runners;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.OneTestExecResult;
import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.main.Main;
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
		
		int count = 1;
		for(String test : super.junitTestList) {
			long starttime = System.currentTimeMillis();
			
			Map<String, OneTestExecResult> singleRun = TestExecUtils.executeTestsInFreshJVM(super.getClassPath(),
					super.getTmpOutputFile(), Collections.singletonList(test));
			result.addExecutionResults(singleRun);
			
			if(Main.showProgress) {
				long endtime = System.currentTimeMillis();
				long elpased = endtime - starttime;
				float seconds = (junitTestList.size() - count)*elpased / (1000);
				System.out.println("Run the: " + count + " / " + junitTestList.size() + " test, still need: " + seconds + " seconds to finish.");
			}
			count++;
		}
		
		return result;
	}
}