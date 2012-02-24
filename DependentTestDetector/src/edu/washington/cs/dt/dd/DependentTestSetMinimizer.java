package edu.washington.cs.dt.dd;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.RESULT;
import edu.washington.cs.dt.util.TestExecUtils;

public class DependentTestSetMinimizer extends AbstractMinimizer<String>{

	public final String classPath;
	public final String tmpOutputFile;
	
	public final String dependentTest;
	public final RESULT intendedResult;
	
	private Map<List<String>, RESULT> cachedResults = new HashMap<List<String>, RESULT>();
	private boolean enablecache = Main.enablecache;

	/**
	 * Running tests + dependentTest will make the dependentTest reveal a different
	 * result than intendedResult.
	 * 
	 * This class finds a minimal subset of tests, called subtests, that running
	 * subtess + dependentTest reveal the same behavior for dependentTest as
	 * running tests + dependentTest
	 * */
	public DependentTestSetMinimizer(List<String> tests,
			String dependentTest, RESULT intendedResult,
			String classPath, String tmpOutputFile) {
		super(tests);
		this.classPath = classPath;
		this.tmpOutputFile = tmpOutputFile;
		this.dependentTest = dependentTest;
		this.intendedResult = intendedResult;
	}

	@Override
	protected boolean is_still_fail(List<String> tests) {
		List<String> exec_tests = new LinkedList<String>();
		exec_tests.addAll(tests);
		exec_tests.add(dependentTest);
		
		//first check the cache if enabled
		RESULT r = null;
		if(this.enablecache && this.cachedResults.containsKey(exec_tests)) {
			r = cachedResults.get(exec_tests);
		} else {
			//execute tests in an isolated JVM
			Map<String, RESULT> results = TestExecUtils.executeTestsInFreshJVM(this.classPath, this.tmpOutputFile, exec_tests);
			//check the result
			r = results.get(this.dependentTest);
			//put into cache
			if(this.enablecache) {
			    this.cachedResults.put(exec_tests, r);
			}
		}
//		System.out.println(r);
//		System.out.println("intended: " + intendedResult);
		if(r.equals(intendedResult)) {
			return true;  //the same as the intended result (i.e., executed in a fixed order)
		} else {
			return false;   //still have different behaviors
		}
	}

}
