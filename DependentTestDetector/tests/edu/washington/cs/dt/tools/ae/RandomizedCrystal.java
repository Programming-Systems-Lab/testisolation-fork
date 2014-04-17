package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestRandomizedDependentTestFinder;

public class RandomizedCrystal {

	public static void main(String[] args) {
	       TestRandomizedDependentTestFinder finder = new TestRandomizedDependentTestFinder();
		   finder.testCrystal_manual();
		   finder.testCrystal_auto();
    }
}
