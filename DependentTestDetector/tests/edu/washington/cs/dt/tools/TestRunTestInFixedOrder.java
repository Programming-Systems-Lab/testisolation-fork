/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.tools;

import java.util.Set;

import edu.washington.cs.dt.TestExecResult;
import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.runners.FixedOrderRunner;
import edu.washington.cs.dt.tools.RunTestInFixedOrder;
import edu.washington.cs.dt.util.Log;
import edu.washington.cs.dt.util.Utils;
import junit.framework.TestCase;

public class TestRunTestInFixedOrder extends TestCase {
	
	public void testExamples() {
		RunTestInFixedOrder.main(new String[]{"--testFile=./tests/edu/washington/cs/dt/main/sampleinput.txt"});
	}
	
	public void testCrystal_manual() {
		Log.logConfig("./fixed_order_crystal_results.txt");
		
		FixedOrderRunner fixedRunner = new FixedOrderRunner(TestRandomizedDependentTestFinder.crystalFile_manual);
		TestExecResults expected_results = fixedRunner.run();
		Utils.checkTrue(expected_results.getExecutionRecords().size() == 1,
				"The size is: " + expected_results.getExecutionRecords().size());
		TestExecResult rs = expected_results.getExecutionRecords().get(0);
		
		//write the results to the log file
		Log.logln("The default execution results: ");
		for(String test : rs.getAllTests()) {
			Log.logln("  Test: " + test + ": " + rs.getResult(test).result);
		}
	}
}
