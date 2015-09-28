package edu.washington.cs.dt.tools;

import java.util.Set;

import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class TestElectricDependentTestFinder extends TestCase {

	public void testToyExamples() {
		this.runTests(TestRandomizedDependentTestFinder.exampleFile, 
				"./electric_toy_results.txt", 1);
		this.runTests(TestRandomizedDependentTestFinder.exampleFile,
				"./electric_toy_results.txt", 2);
		this.runTests(TestRandomizedDependentTestFinder.exampleFile,
				"./bounded_3_toy_results.txt", 3);
	}
	
	public void testJFreeChart_manual() {
		this.runTests(TestRandomizedDependentTestFinder.jfreechart_manual, 
				"./electric_jfreechart_manual.txt", 1);
	}
	
	public void testJFreeChart_manual_2() {
		this.runTests(TestRandomizedDependentTestFinder.jfreechart_manual, 
				"./electric_jfreechart_manual.txt", 2);
	}
	
	public void testXMLSecurity_manual() {
//		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual, 
//				"./electric_xmlsecurity_manual.txt", 1);
		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual,
				"./electric_xmlsecurity_manual.txt", 2);
//		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual,
//				"./bounded_3_xmlsecurity_manual.txt", 3);
	}
	
	public void testXMLSecurity_manual_safe() {
		this.runTests("./tests/edu/washington/cs/dt/tools/xml-security-safe-manual-tests.txt", 
				"./electric_xmlsecurity_safe_manual.txt", 1);
//		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual,
//				"./electric_xmlsecurity_safe_manual.txt", 2);
//		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual,
//				"./bounded_3_xmlsecurity_manual.txt", 3);
	}
	
	public void testCrystal_manual() {
//		this.runTests(TestRandomizedDependentTestFinder.crystalFile_manual, 
//				"./electric_crystal_manual.txt", 1);
		this.runTests(TestRandomizedDependentTestFinder.crystalFile_manual,
				"./electric_crystal_manual.txt", 2);
	}
	
	public void testJodatime_manual() {
		this.runTests(TestRandomizedDependentTestFinder.jodatimeFile_manual, 
				"./electric_jodatime_manual.txt", 1);
	}
	
	public void testJodatime_manual_sample_2() {
		runTestsWithSampling("./electric_jodatime_sample_2.txt",
				TestRandomizedDependentTestFinder.jodatimeFile_manual, 2,
				1000, 0.03f);
	}
	
	public void testJodatime_auto_sample_2() {
		runTestsWithSampling("./electric_jodatime_auto_sample_2.txt",
				TestRandomizedDependentTestFinder.jodatimeFile_auto, 2,
				1000, 0.03f);
	}
	
	
	//run
	public void testSynoptic_manual() {
//		this.runTests(TestRandomizedDependentTestFinder.synopticFile_manual, 
//				"./electric_synoptic_manual.txt", 1);
		this.runTests(TestRandomizedDependentTestFinder.synopticFile_manual, 
				"./electric_synoptic_manual.txt", 2);
	}
	
	
	
	//for automated tests
	public void testXMLSecurity_auto() {
		this.runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_auto, 
				"./electric_xmlsecurity_auto.txt", 1);
	}
	
	//run
	public void testXMLSecurity_auto_sample_2() {
		this.runTestsWithSampling("./electric_xmlsecurity_sampled_auto.txt",
				TestRandomizedDependentTestFinder.xmlSecurityFile_auto, 
				 2, 1000, 0.03f);
	}
	
	public void testCrystal_auto() {
		this.runTests(TestRandomizedDependentTestFinder.crystalFile_auto, 
				"./electric_crystal_auto.txt", 1);
	}
	
	public void testCrystal_auto_sample_2() {
		this.runTestsWithSampling("./electric_crystal_sampled_auto.txt",
				TestRandomizedDependentTestFinder.crystalFile_auto, 
				 2, 1000, 0.03f);
	}
	
	public void testJodatime_auto() {
		this.runTests(TestRandomizedDependentTestFinder.jodatimeFile_auto, 
				"./electric_jodattime_auto.txt", 1);
	}
	
	//run
	public void testSynoptic_auto_1() {
		this.runTests(TestRandomizedDependentTestFinder.synopticFile_auto,
				"./electric_synoptic_auto.txt", 1);
	}
	
	public void testSynoptic_auto_test() {
		this.runTests(TestRandomizedDependentTestFinder.synopticFile_auto,
				"./electric_synoptic_auto_TEST.txt", 1);
	}
	
	public void testSynoptic_auto_sampled_2() {
		this.runTestsWithSampling("./electric_synoptic_sampled_auto.txt",
				TestRandomizedDependentTestFinder.synopticFile_auto,
				 2, 1000, 0.03f);
	}
	
	public void runTests(String testFile, String fileName, int k) {
		Log.logConfig(fileName);
		ElectricDependentTestFinder.verbose = true;
		ElectricDependentTestFinder boundedDTFinder
		    = new ElectricDependentTestFinder(testFile);
		Set<String> dts = boundedDTFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void runTestsWithSampling(String logFileName, String testFile, int k,
			int sampleSize, float rate) {
//		Log.logConfig(logFileName);
//		ElectricDependentTestFinder.verbose = true;
//		ElectricDependentTestFinder boundedDTFinder
//		    = new ElectricDependentTestFinder(testFile);
//		boundedDTFinder.useRandomDiscarder(sampleSize, rate);
//		Set<String> dts = boundedDTFinder.findDependentTests();
//		for(String t : dts) {
//			System.out.println("    " + t);
//		}
		throw new UnsupportedOperationException();
	}
	
	public static void main(String[] args) {
		TestElectricDependentTestFinder finder = new TestElectricDependentTestFinder();
		
		//for manual tests
		finder.testJodatime_manual();  //k = 1
		finder.testXMLSecurity_manual(); //k = 1, k = 2
		finder.testCrystal_manual(); //k = 1, k = 2
		finder.testSynoptic_manual(); //k=1, k = 2
		
		//for auto tests
		finder.testJodatime_auto();
		finder.testXMLSecurity_auto();
		finder.testCrystal_auto();
		finder.testSynoptic_auto_test();
	}
}