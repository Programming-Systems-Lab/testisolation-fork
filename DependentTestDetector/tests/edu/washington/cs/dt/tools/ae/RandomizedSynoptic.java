package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestRandomizedDependentTestFinder;

public class RandomizedSynoptic {

	public static void main(String[] args) {
	       TestRandomizedDependentTestFinder finder = new TestRandomizedDependentTestFinder();
	       finder.testSynoptic_manual();
		   finder.testSynoptic_auto();
    }
}
