package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestBoundedDependentTestFinder;

public class ExhaustiveXMLSecurity {

    public static void main(String[] args) {
    	TestBoundedDependentTestFinder finder = new TestBoundedDependentTestFinder();
		finder.testXMLSecurity_manual(); //k = 1, k = 2
		finder.testXMLSecurity_auto();
	}
    
}
