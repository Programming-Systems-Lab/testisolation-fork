package edu.washington.cs.dt.runners;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.OneTestExecResult;
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
		
		int count = 1;
		for(String test : super.junitTestList) {
			long starttime = System.currentTimeMillis();
			
			Map<String, OneTestExecResult> singleRun = TestExecUtils.executeTestsInFreshJVM(super.getClassPath(),
					super.getTmpOutputFile(), Collections.singletonList(test));
			result.addExecutionResults(singleRun);
			
			//record the used time
			long endtime = System.currentTimeMillis();
			float elapsed = (float)(endtime - starttime) / 1000;
			executionTime = executionTime + elapsed;
			if(Main.showProgress) {
				float estLeftTime = (executionTime / count)*(junitTestList.size() - count);
				System.out.println("Run the: " + count + " / " + junitTestList.size() + " test using: "
						+ executionTime + " seconds, still need: " + estLeftTime + " seconds to finish.");
			}
			count++;
		}
		
		System.out.println("Total execution time: " + executionTime + " seconds");
		
		return result;
	}
}