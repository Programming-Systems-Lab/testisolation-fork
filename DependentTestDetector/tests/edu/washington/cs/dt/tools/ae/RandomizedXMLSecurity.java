package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestRandomizedDependentTestFinder;

public class RandomizedXMLSecurity {

	public static void main(String[] args) {
	       TestRandomizedDependentTestFinder finder = new TestRandomizedDependentTestFinder();
	       finder.testXMLSecurity_manual();
		   finder.testXMLSecurity_auto();
    }
	
}
