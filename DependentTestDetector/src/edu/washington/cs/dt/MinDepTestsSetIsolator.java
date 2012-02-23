package edu.washington.cs.dt;

import java.util.LinkedHashMap;
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
public class MinDepTestsSetIsolator {
	
	public final List<String> tests;
	
	public final Class<? extends AbstractTestRunner> runnerClz;
	public final AbstractTestRunner fixedOrderRunner;
	public final AbstractTestRunner defaultRunner;
	
	private String classPath = "";
	private String tmpOutputFile = "./tmpFile.txt";
	
	public MinDepTestsSetIsolator(List<String> tests, Class<? extends AbstractTestRunner> clz) {
		this.tests = tests;
		this.runnerClz = clz;
		this.defaultRunner = new IsolationRunner(this.tests);
		this.fixedOrderRunner = new FixedOrderRunner(this.tests);
	}
	
	public Map<String, List<String>> isolateForIsolation() {
		Map<String, List<String>> depMap = new LinkedHashMap<String, List<String>>();
		
		TestExecResults fixedOrderResults = this.fixedOrderRunner.run();
		TestExecResults defaultResults = this.defaultRunner.run();
		
		List<TestExecResult> foRecords = fixedOrderResults.getExecutionRecords();
		Utils.checkTrue(foRecords.size() == 1, "");
		Map<String, RESULT> foResults = foRecords.get(0).singleRun;
		
		Map<String, RESULT> isoResults = new LinkedHashMap<String, RESULT>();
		for(TestExecResult r : defaultResults.getExecutionRecords()) {
			isoResults.putAll(r.singleRun);
		}
		Utils.checkTrue(foResults.size() == isoResults.size(), "");
		
		for(String test : foResults.keySet()) {
			RESULT intended = foResults.get(test);
			RESULT isoResult = isoResults.get(test);
			if(isoResult.equals(intended)) {
				continue;
			}
			//use delta debugging to minimze
			int index = this.tests.indexOf(test);
			List<String> depTests = this.tests.subList(0, index);
			
			DependentTestSetMinimizer minimizer = new DependentTestSetMinimizer(depTests, test,
					isoResult, this.classPath, this.tmpOutputFile);
			List<String> minSet = minimizer.minimize();
			depMap.put(test, minSet);
		}
		
		return depMap;
	}
}