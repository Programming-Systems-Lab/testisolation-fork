package edu.washington.cs.dt.main;

import edu.washington.cs.dt.tools.RunTestInFixedOrder;
import junit.framework.TestCase;

public class TestRunTestInFixedOrder extends TestCase {
	
	public void testExamples() {
		RunTestInFixedOrder.main(new String[]{"--testFile=./tests/edu/washington/cs/dt/main/sampleinput.txt"});
	}

}
