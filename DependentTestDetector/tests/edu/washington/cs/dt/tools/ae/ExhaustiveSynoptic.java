package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestBoundedDependentTestFinder;

public class ExhaustiveSynoptic {

    public static void main(String[] args) {
    	TestBoundedDependentTestFinder finder = new TestBoundedDependentTestFinder();
		
		finder.testSynoptic_manual_sampled(); //k=1, k = 2
//		finder.testSynoptic_auto_test();
	}
}
