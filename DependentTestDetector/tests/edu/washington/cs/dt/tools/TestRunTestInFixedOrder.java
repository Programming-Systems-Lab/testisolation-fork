/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.tools;

import edu.washington.cs.dt.tools.RunTestInFixedOrder;
import junit.framework.TestCase;

public class TestRunTestInFixedOrder extends TestCase {
	
	public void testExamples() {
		RunTestInFixedOrder.main(new String[]{"--testFile=./tests/edu/washington/cs/dt/main/sampleinput.txt"});
	}
}
