package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestDependenceAwareTestFinder;

public class DepAwareJodatime {

	public static void main(String[] args) {
        TestDependenceAwareTestFinder finder = new TestDependenceAwareTestFinder();
		
		//evalaute manual tests
		finder.testJodatimeIsolation_manual();
//		finder.testJodatimePairwise_manual();
		//evalaute auto tests
//		finder.testJodatime_1_auto();
//		finder.testJodatime_2_sample_auto();
	}
}
