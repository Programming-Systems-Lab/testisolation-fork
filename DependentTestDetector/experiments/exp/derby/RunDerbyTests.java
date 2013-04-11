/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package exp.derby;

import edu.washington.cs.dt.tools.RunTestInFixedOrder;
import junit.framework.TestCase;

//
//Ignore this class, it does not run Derby's tests successfully.
public class RunDerbyTests extends TestCase {
	
	String inputTestFile = "./experiments-subjects/derby/derby-unit-tests.txt";

	public void testSampleTestsFixedOrder() {
		inputTestFile = "./experiments-subjects/derby/derby-sample-tests.txt";
		String[] args = new String[] {
				"--testFile=" + inputTestFile,
				"--outputFile=./experiments-subjects/derby/dt-derby-sample-tests-fixedorder.txt"
		};
		RunTestInFixedOrder.main(args);
	}
	
	public void testRunTestsInFixedOrder() {
		String[] args = new String[] {
				"--testFile=" + inputTestFile,
				"--outputFile=./experiments-subjects/derby/dt-derby-fixedorder.txt"
		};
		RunTestInFixedOrder.main(args);
	}
}
