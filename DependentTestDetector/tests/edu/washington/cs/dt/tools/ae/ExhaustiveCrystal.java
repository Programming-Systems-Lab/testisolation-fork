package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestBoundedDependentTestFinder;

public class ExhaustiveCrystal {

	public static void main(String[] args) {
		
		TestBoundedDependentTestFinder finder = new TestBoundedDependentTestFinder();
		//for manual tests
		finder.testCrystal_manual(); //k = 1, k = 2
//		finder.testCrystal_auto();
	}
}
