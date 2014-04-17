package edu.washington.cs.dt.tools.ae;

import edu.washington.cs.dt.tools.TestRandomizedDependentTestFinder;

public class RandomizedXMLSecurity {

	public static void main(String[] args) {
		if(args.length == 1) {
			try {
				TestRandomizedDependentTestFinder.shuffle_times = Integer.parseInt(args[0]);
			    System.out.println("Setting shuffle times to: "
				    + TestRandomizedDependentTestFinder.shuffle_times);
			} catch (Throwable e) {
				System.out.println("It should be an integer, but " + args[0] + " is provided.");
				return;
			}
		}
		
	       TestRandomizedDependentTestFinder finder = new TestRandomizedDependentTestFinder();
	       finder.testXMLSecurity_manual();
		   finder.testXMLSecurity_auto();
    }
	
}
