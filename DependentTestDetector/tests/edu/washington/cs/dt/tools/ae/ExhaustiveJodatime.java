package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestBoundedDependentTestFinder;

public class ExhaustiveJodatime {

    public static void main(String[] args) {
    	TestBoundedDependentTestFinder finder = new TestBoundedDependentTestFinder();
		finder.testJodatime_manual();  //k = 1
//		finder.testJodatime_auto();
	}
}
