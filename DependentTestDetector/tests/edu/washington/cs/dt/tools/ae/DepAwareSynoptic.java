package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestDependenceAwareTestFinder;

public class DepAwareSynoptic {

	public static void main(String[] args) {
        TestDependenceAwareTestFinder finder = new TestDependenceAwareTestFinder();
		
		//evalaute manual tests
		finder.testSynopticIsolation_manual();
		finder.testSynopticPairwise_manual();
		
		//evalaute auto tests
		finder.testSynoptic_2_auto_sample();
	}
}
