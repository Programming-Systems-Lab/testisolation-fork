package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestRandomizedDependentTestFinder;

public class RandomizedJodatime {

	public static void main(String[] args) {
       TestRandomizedDependentTestFinder finder = new TestRandomizedDependentTestFinder();
		finder.testJodaTime_manual();
	}
}
