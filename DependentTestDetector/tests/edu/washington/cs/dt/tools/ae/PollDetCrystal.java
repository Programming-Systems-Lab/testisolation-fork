package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestBoundedDependentTestFinder;
import edu.washington.cs.dt.tools.TestElectricDependentTestFinder;
import edu.washington.cs.dt.tools.TestPollutingTestFinder;

public class PollDetCrystal {

	public static void main(String[] args) {
		
		TestPollutingTestFinder finder = new TestPollutingTestFinder();
		//for manual tests
		finder.testCrystal_manual(); //k = 1, k = 2
//		finder.testCrystal_auto();
	}
}
