package edu.washington.cs.dt;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.dd.DependentTestSetMinimizer;
import edu.washington.cs.dt.runners.AbstractTestRunner;
import edu.washington.cs.dt.runners.FixedOrderRunner;
import edu.washington.cs.dt.runners.IsolationRunner;
import edu.washington.cs.dt.util.RESULT;
import edu.washington.cs.dt.util.Utils;

/**
 * Need to configure paths
 * */
public class DependentTestIdentifier {
	
	public final List<String> tests;
	
	private String classPath = "";
	private String tmpOutputFile = "./tmpFile.txt";
	
	private boolean minimize = false;
	
	public DependentTestIdentifier(List<String> tests) {
		this.tests = tests;
	}
	
	public void setMinimize(boolean minimize) {
		this.minimize = minimize;
	}
	
	public void setClasspath(String cp) {
		this.classPath = cp;
	}
	
	public void setTmpOutputFile(String tmpFile) {
		this.tmpOutputFile = tmpFile;
	}
	
	public List<TestExecResultsDelta> findDependenceForIsolation() {
		AbstractTestRunner fixedOrderRunner = new FixedOrderRunner(this.tests);
		fixedOrderRunner.setClassPath(this.classPath);
		fixedOrderRunner.setTmpOutputFile(this.tmpOutputFile);
		
		AbstractTestRunner isolationRunner = new IsolationRunner(this.tests);
		isolationRunner.setClassPath(this.classPath);
		isolationRunner.setTmpOutputFile(this.tmpOutputFile);
		
		TestExecResults fixedOrderResults = fixedOrderRunner.run();
		TestExecResults isolationResults = isolationRunner.run();
		
		List<TestExecResult> foRecords = fixedOrderResults.getExecutionRecords();
		Utils.checkTrue(foRecords.size() == 1, "");
		
		//see the difference, any behavior difference is treated as dependent tests
		TestExecResult intendedResult = fixedOrderResults.getExecutionRecords().get(0);
		TestExecResultsDifferentior differ = new TestExecResultsDifferentior(intendedResult, isolationResults);
		List<TestExecResultsDelta> deltas = differ.diffResults();
		
		if(!minimize) {
			return deltas;
		}
		
		List<TestExecResultsDelta> minimized = new LinkedList<TestExecResultsDelta>();
		for(TestExecResultsDelta d : deltas) {
			Utils.checkTrue(d.dependentTests.size() == 0, "");
			DependentTestSetMinimizer minimizer = new DependentTestSetMinimizer(d.intendedPreTests, d.testName,
					d.divergentResult, this.classPath, this.tmpOutputFile);
			List<String> minList = minimizer.minimize();
			TestExecResultsDelta simplifiedDelta = new TestExecResultsDelta(d.testName, d.intendedResult, minList,
					d.divergentResult, d.dependentTests);
			minimized.add(simplifiedDelta);
		}
		
		return minimized;
	}
}