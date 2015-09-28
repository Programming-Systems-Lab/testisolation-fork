package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestBoundedDependentTestFinder;
import edu.washington.cs.dt.tools.TestElectricDependentTestFinder;

public class ElectricJodatime {

    public static void main(String[] args) {
    	TestElectricDependentTestFinder finder = new TestElectricDependentTestFinder();
		finder.testJodatime_manual();  //k = 1
//		finder.testJodatime_auto();
	}
}
