package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestDependenceAwareTestFinder;

public class DepAwareCrystal {
	public static void main(String[] args) {
       TestDependenceAwareTestFinder finder = new TestDependenceAwareTestFinder();
		
		//evalaute manual tests
		finder.testCrystalIsolation_manual();
		finder.testCrystalPairwise_manual();
		finder.testCrystal_1_auto();
		finder.testCrystal_2_sample_auto();
	}
}
