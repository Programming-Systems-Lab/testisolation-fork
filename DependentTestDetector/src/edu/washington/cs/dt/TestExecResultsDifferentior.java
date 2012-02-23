package edu.washington.cs.dt;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.util.RESULT;
import edu.washington.cs.dt.util.Utils;

public class TestExecResultsDifferentior {
	
	public final TestExecResult intendedResults; //the fixed-order execution
	public final TestExecResults comparingResults; //the results for comparing
	
	public TestExecResultsDifferentior(TestExecResult intendedResults,
			TestExecResults comparingResults) {
		this.intendedResults = intendedResults;
		this.comparingResults = comparingResults;
	}
	
	public List<TestExecResultsDelta> diffResults() {
		List<String> intendedExecutedTests = this.intendedResults.getAllTests();
		
		List<TestExecResultsDelta> rets = new LinkedList<TestExecResultsDelta>();
		for(TestExecResult cr : comparingResults.getExecutionRecords()) {
			Map<String, RESULT> singleRun = cr.singleRun;
			List<String> executedTests = new LinkedList<String>();
			//add the key set in order
			executedTests.addAll(singleRun.keySet());
			for(int i = 0; i < executedTests.size(); i++) {
				String t = executedTests.get(i);
				RESULT r = singleRun.get(t);
				//lookup intended results
				RESULT intend = this.intendedResults.getResult(t);
				Utils.checkNull(intend, "The test: " + t + " does not exist in intended behaviors.");
				
				if(!r.equals(intend)) {
					List<String> deps = executedTests.subList(0, i);
					List<String> preExecutedTests = intendedExecutedTests.subList(0, intendedExecutedTests.indexOf(t));
					TestExecResultsDelta delta = new TestExecResultsDelta(t, intend, preExecutedTests, r, deps);
					rets.add(delta);
				}
			}
		}
		
		return rets;
	}
	
}

