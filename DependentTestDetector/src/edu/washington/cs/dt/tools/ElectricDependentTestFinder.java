package edu.washington.cs.dt.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.washington.cs.dt.OneTestExecResult;
import edu.washington.cs.dt.TestExecResult;
import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.runners.ElectricRunner;
import edu.washington.cs.dt.runners.FixedOrderRunner;
import edu.washington.cs.dt.util.Files;
import edu.washington.cs.dt.util.Log;
import edu.washington.cs.dt.util.PermutationGenerator;
import edu.washington.cs.dt.util.Utils;

public class ElectricDependentTestFinder {

	private final List<String> defaultTestList;
	
	public static boolean verbose = true;
	
	public static boolean only_compare_outcome = true;
	
	private Set<String> safeTests = new HashSet<String>();
	
	public ElectricDependentTestFinder(List<String> tests) {
		Utils.checkNull(tests, "Should not be null");
		this.defaultTestList = new ArrayList<String>();
		this.defaultTestList.addAll(tests);
	}
	
	public ElectricDependentTestFinder(String fileName) {
		this(Files.readWholeNoExp(fileName));
	}
	

	public void addSafeTests(Collection<String> tests) {
		this.safeTests.addAll(tests);
	}
	
	public Set<String> findDependentTests() {
		long startTime = System.currentTimeMillis();
		
		//use linked hash set to keep the original order
		Set<String> depTests = new LinkedHashSet<String>();
		
		//first execute all tests in the default order
		ElectricRunner fixedRunner = new ElectricRunner(this.defaultTestList);
		TestExecResults expected_results = fixedRunner.run();
		Utils.checkTrue(expected_results.getExecutionRecords().size() == 1,
				"The size is: " + expected_results.getExecutionRecords().size());
		TestExecResult rs = expected_results.getExecutionRecords().get(0);
		if(verbose) {
			Log.logln("The default execution results: ");
			for(String test : rs.getAllTests()) {
				Log.logln("  Test: " + test + ": " + rs.getResult(test).result);
			}
		}
		
		Log.logln("End, total time cost: " + (System.currentTimeMillis() - startTime)/1000 + " seconds");
		Log.logln("Number of dependent tests: " + depTests.size());
		for(String dt : depTests) {
			Log.logln("    " + dt);
		}
		return depTests;
	}
//	
//	private Set<String> identifyTestsWithDifferentResults(TestExecResults expected_results,
//			TestExecResults exec_results) {
//		Set<String> diffTests = new LinkedHashSet<String>();
//		//check the number of JVM launches
//		Utils.checkTrue(expected_results.getExecutionRecords().size() == 1, "Number of JVM launches: "
//				+ expected_results.getExecutionRecords().size());
//		Utils.checkTrue(exec_results.getExecutionRecords().size() == 1, "Number of JVM launches: "
//				+ exec_results.getExecutionRecords().size());
//		
//		//get the test results
//		TestExecResult default_results = expected_results.getExecutionRecords().get(0);
//		TestExecResult actual_results = exec_results.getExecutionRecords().get(0);
//		
//		//check the number of the tests
//		Utils.checkTrue(default_results.getAllTests().size() == this.defaultTestList.size(),
//				"Size not equal: " + default_results.getAllTests().size());
//		Utils.checkTrue(actual_results.getAllTests().size() == this.k,
//				"Size not equal: " + actual_results.getAllTests().size() + ", this k: " + this.k);
//		
//		//then compare the test execution outcome
//		for(String actualTest : actual_results.getAllTests()) {
//			OneTestExecResult defaultTestResult = default_results.getResult(actualTest);
//			OneTestExecResult actualTestResult = actual_results.getResult(actualTest);
//			
//			//compare the results
//			if(!sameResults(defaultTestResult, actualTestResult)) {
//				diffTests.add(actualTest);
//				if(verbose) {
//					Log.logln("  Diff Test: " + actualTest);
//					Log.logln("    default result: " + defaultTestResult.result);
//					Log.logln("    result in this run: " + actualTestResult.result);
//				}
//			}
//		}
//		
//		return diffTests;
//	}
	
	private boolean sameResults(OneTestExecResult r1, OneTestExecResult r2) {
		if(only_compare_outcome) {
			return r1.result.equals(r2.result);
		} else {
			return r1.equals(r2);
		}
	}
}
