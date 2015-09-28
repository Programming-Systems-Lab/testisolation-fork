package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestBoundedDependentTestFinder;
import edu.washington.cs.dt.tools.TestElectricDependentTestFinder;

public class ElectricCrystal {

	public static void main(String[] args) {
		
		TestElectricDependentTestFinder finder = new TestElectricDependentTestFinder();
		//for manual tests
		finder.testCrystal_manual(); //k = 1, k = 2
//		finder.testCrystal_auto();
	}
}
