package edu.washington.cs.dt.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import plume.Option;

import edu.washington.cs.dt.OneTestExecResult;
import edu.washington.cs.dt.TestExecResult;
import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.runners.FixedOrderRunner;
import edu.washington.cs.dt.util.Files;
import edu.washington.cs.dt.util.Log;
import edu.washington.cs.dt.util.Utils;

public class ReversedOrderedDependentTestFinder {
	
	@Option("Compare outcome or also compare stack traces")
	public static boolean only_compare_outcome = true;
	
	@Option("Dump all details to a log file")
	public static boolean verbose = false;

	public final List<String> defaultTestList;
	
	public ReversedOrderedDependentTestFinder(List<String> testList) {
		Utils.checkNull(testList, "Test list cannot be null.");
		this.defaultTestList = new LinkedList<String>();
		this.defaultTestList.addAll(testList);
	}
	
	public ReversedOrderedDependentTestFinder(String fileName) {
		this(Files.readWholeNoExp(fileName));
	}
	
	
	public Set<String> findDependentTests() {
		long startTime = System.currentTimeMillis();
		
		//use linked hash set to keep the original order
		Set<String> depTests = new LinkedHashSet<String>();
		
		//first execute all tests in the default order
		FixedOrderRunner fixedRunner = new FixedOrderRunner(this.defaultTestList);
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
		
		//then start to shuffle the test suite, and executed the shuffled suite
		//and then observe the results.
		
		List<String> reversedTests = new ArrayList<String>();
		reversedTests.addAll(this.defaultTestList);
		Collections.reverse(reversedTests);
		
		System.out.println("Executing in a reversed order: ");
		if(verbose) {
			System.out.println("  shuffled tests: " + reversedTests);
		}
		FixedOrderRunner runner = new FixedOrderRunner(reversedTests);
		TestExecResults exec_results = runner.run();
		//find new dependent tests
		Set<String> diffTests = this.identifyTestsWithDifferentResults(expected_results, exec_results);
		depTests.addAll(diffTests);
		System.out.println("   Num of dependent tests: " + depTests.size());
		
		Log.logln("Time cost: " + (System.currentTimeMillis() - startTime)/1000 + " second");
		Log.logln("Number of dependent tests: " + depTests.size());
		for(String dt : depTests) {
			Log.logln("   " + dt);
		}
		
		
		return depTests;
	}
	
	private Set<String> identifyTestsWithDifferentResults(TestExecResults expected_results,
			TestExecResults exec_results) {
		Set<String> diffTests = new LinkedHashSet<String>();
		//check the number of JVM launches
		Utils.checkTrue(expected_results.getExecutionRecords().size() == 1, "Number of JVM launches: "
				+ expected_results.getExecutionRecords().size());
		Utils.checkTrue(exec_results.getExecutionRecords().size() == 1, "Number of JVM launches: "
				+ exec_results.getExecutionRecords().size());
		
		//get the test results
		TestExecResult default_results = expected_results.getExecutionRecords().get(0);
		TestExecResult actual_results = exec_results.getExecutionRecords().get(0);
		
		//check the number of the tests
		Utils.checkTrue(default_results.getAllTests().size() == actual_results.getAllTests().size(),
				"Size not equal: " + default_results.getAllTests().size() + " vs. "
		         +  actual_results.getAllTests().size());
		
		//then compare the test execution outcome
		for(String test : default_results.getAllTests()) {
			OneTestExecResult defaultTestResult = default_results.getResult(test);
			OneTestExecResult actualTestResult = actual_results.getResult(test);
			
			
			//compare the results
			if(!sameResults(defaultTestResult, actualTestResult)) {
				diffTests.add(test);
				if(verbose) {
					Log.logln("  Diff Test: " + test);
					Log.logln("    default result: " + defaultTestResult.result);
					Log.logln("    shuffled result: " + actualTestResult.result);
				}
			}
		}
		
		return diffTests;
	}
	
	private boolean sameResults(OneTestExecResult r1, OneTestExecResult r2) {
		if(only_compare_outcome) {
			return r1.result.equals(r2.result);
		} else {
			return r1.equals(r2);
		}
	}
}
