package edu.washington.cs.dt.tools;

import java.util.Set;

import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class TestBoundedDependentTestFinder extends TestCase {

	public void testToyExamples() {
		this.runTests(TestRandomizedDependentTestFinder.exampleFile, 
				"./bounded_1_toy_results.txt", 1);
		this.runTests(TestRandomizedDependentTestFinder.exampleFile,
				"./bounded_2_toy_results.txt", 2);
		this.runTests(TestRandomizedDependentTestFinder.exampleFile,
				"./bounded_3_toy_results.txt", 3);
	}
	
	public void testXMLSecurity_manual() {
		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual, 
				"./bounded_1_xmlsecurity_manualtxt", 1);
		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual,
				"./bounded_2_xmlsecurity_manual.txt", 2);
//		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual,
//				"./bounded_3_xmlsecurity_manual.txt", 3);
	}
	
	public void testXMLSecurity_manual_safe() {
		this.runTests("./tests/edu/washington/cs/dt/tools/xml-security-safe-manual-tests.txt", 
				"./bounded_1_xmlsecurity_safe_manualtxt", 1);
		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual,
				"./bounded_2_xmlsecurity_safe_manual.txt", 2);
//		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual,
//				"./bounded_3_xmlsecurity_manual.txt", 3);
	}
	
	public void testCrystal_manual() {
		this.runTests(TestRandomizedDependentTestFinder.crystalFile_manual, 
				"./bounded_1_crystal_manualtxt", 1);
		this.runTests(TestRandomizedDependentTestFinder.crystalFile_manual,
				"./bounded_2_crystal_manual.txt", 2);
	}
	
	public void testJodatime_manual() {
		this.runTests(TestRandomizedDependentTestFinder.jodatimeFile_manual, 
				"./bounded_1_jodatime_manualtxt", 1);
	}
	
	//for automated tests
	public void testXMLSecurity_auto() {
		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_auto, 
				"./bounded_1_xmlsecurity_auto.txt", 1);
	}
	
	public void testCrystal_auto() {
		this.runTests(TestRandomizedDependentTestFinder.crystalFile_auto, 
				"./bounded_1_crystal_auto.txt", 1);
	}
	
	public void testJodatime_auto() {
		this.runTests(TestRandomizedDependentTestFinder.jodatimeFile_auto, 
				"./bounded_1_jodattime_auto.txt", 1);
	}
	
	
	
	public void runTests(String testFile, String fileName, int k) {
		Log.logConfig(fileName);
		BoundedDependentTestFinder.verbose = true;
		BoundedDependentTestFinder boundedDTFinder
		    = new BoundedDependentTestFinder(testFile, k);
		Set<String> dts = boundedDTFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
}
