package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestBoundedDependentTestFinder;
import edu.washington.cs.dt.tools.TestElectricDependentTestFinder;

public class ElectricSynoptic {

    public static void main(String[] args) {
    	TestElectricDependentTestFinder finder = new TestElectricDependentTestFinder();
		
		finder.testSynoptic_manual(); //k=1, k = 2
//		finder.testSynoptic_auto_test();
	}
}
