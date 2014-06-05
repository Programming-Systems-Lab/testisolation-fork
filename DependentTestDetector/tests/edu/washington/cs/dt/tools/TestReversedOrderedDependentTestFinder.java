package edu.washington.cs.dt.tools;

import java.util.Set;

import junit.framework.TestCase;

import edu.washington.cs.dt.util.Log;

public class TestReversedOrderedDependentTestFinder extends TestCase {

	public void testJodaTime_manual() {
		Log.logConfig("./reversed_jodatime_results.txt");
		ReversedOrderedDependentTestFinder.verbose = true;
		ReversedOrderedDependentTestFinder finder
		    = new ReversedOrderedDependentTestFinder(TestRandomizedDependentTestFinder.jodatimeFile_manual);
		Set<String> dts = finder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testXMLSecurity_manual() {
		Log.logConfig("./reversed_xmlsecurity_results.txt");
		ReversedOrderedDependentTestFinder.verbose = true;
		ReversedOrderedDependentTestFinder finder
		    = new ReversedOrderedDependentTestFinder(TestRandomizedDependentTestFinder.xmlSecurityFile_manual);
		Set<String> dts = finder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testXMLSecurity_auto() {
		Log.logConfig("./reversed_xmlsecurity_auto_tests_results.txt");
		ReversedOrderedDependentTestFinder.verbose = true;
		ReversedOrderedDependentTestFinder finder
		    = new ReversedOrderedDependentTestFinder(TestRandomizedDependentTestFinder.xmlSecurityFile_auto);
		Set<String> dts = finder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testCrystal_manual() {
		Log.logConfig("./reversed_crystal_results.txt");
		ReversedOrderedDependentTestFinder.verbose = true;
		ReversedOrderedDependentTestFinder finder = new ReversedOrderedDependentTestFinder(TestRandomizedDependentTestFinder.crystalFile_manual);
		Set<String> dts = finder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testCrystal_auto() {
		Log.logConfig("./reversed_crystal_auto_tests_results.txt");
		ReversedOrderedDependentTestFinder.verbose = true;
		ReversedOrderedDependentTestFinder finder = new ReversedOrderedDependentTestFinder(TestRandomizedDependentTestFinder.crystalFile_auto);
		Set<String> dts = finder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testSynoptic_manual() {
		Log.logConfig("./reversed_synoptic_results.txt");
		ReversedOrderedDependentTestFinder.verbose = true;
		ReversedOrderedDependentTestFinder finder = new ReversedOrderedDependentTestFinder(TestRandomizedDependentTestFinder.synopticFile_manual);
		Set<String> dts = finder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testSynoptic_auto() {
		Log.logConfig("./reversed_synoptic_auto_tests_results.txt");
		ReversedOrderedDependentTestFinder.verbose = true;
		ReversedOrderedDependentTestFinder finder = new ReversedOrderedDependentTestFinder(TestRandomizedDependentTestFinder.synopticFile_auto);
		Set<String> dts = finder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
}
