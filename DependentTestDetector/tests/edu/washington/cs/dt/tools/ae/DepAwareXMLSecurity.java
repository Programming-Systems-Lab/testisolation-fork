package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestDependenceAwareTestFinder;

public class DepAwareXMLSecurity {

	public static void main(String[] args) {
        TestDependenceAwareTestFinder finder = new TestDependenceAwareTestFinder();
		
		//evalaute manual tests
		finder.testXMLSecurityPairwise_manual();
		finder.testXMLSecurity_1_auto();
		finder.testXMLSecurity_2_auto();
	}
	
}
