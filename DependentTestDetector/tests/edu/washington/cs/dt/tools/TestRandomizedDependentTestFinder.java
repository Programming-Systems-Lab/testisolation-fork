/** Copyright 2013 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.tools;

import java.util.Set;

import edu.washington.cs.dt.util.Log;

import junit.framework.TestCase;

public class TestRandomizedDependentTestFinder extends TestCase {

	
	final static String exampleFile = "./tests/edu/washington/cs/dt/main/sampleinput.txt";
	
	final static String xmlSecurityFile_manual = "./tests/edu/washington/cs/dt/tools/xmlsecurity-all-manual-tests.txt";
	final static String crystalFile_manual = "./tests/edu/washington/cs/dt/tools/crystal-all-manual-tests.txt";
	final static String jodatimeFile_manual = "./tests/edu/washington/cs/dt/tools/jodatime-all-manual-tests.txt";
	final static String synopticFile_manual = "./tests/edu/washington/cs/dt/tools/synoptic-all-manual-tests.txt";
	final static String jfreechart_manual = "./tests/edu/washington/cs/dt/tools/jfreechart-all-manual-tests.txt";
	final static String jopt_manual = "./tests/edu/washington/cs/dt/tools/jopt-manual-tests.txt";
	
	final static String jfreechart_tmp = "./tmp-jfreechart.txt";
	
	final static String xmlSecurityFile_auto = "./tests/edu/washington/cs/dt/tools/xmlsecurity-auto-test-list.txt";
	final static String crystalFile_auto = "./tests/edu/washington/cs/dt/tools/crystal-auto-test-list.txt";
	final static String jodatimeFile_auto = "./tests/edu/washington/cs/dt/tools/jodattime-auto-test-list.txt";
	final static String synopticFile_auto = "./tests/edu/washington/cs/dt/tools/synoptic-auto-test-list.txt";
	
	public static int shuffle_times = 100;
	
	public void testToyExamples() {
		Log.logConfig("./randomized_toy_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(this.exampleFile);
		randomizedFinder.setTrialNum(2);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testJOpt() {
		Log.logConfig("./manual_jopt_manual_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(jopt_manual);
		randomizedFinder.setTrialNum(100);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testJFreeChart() {
		Log.logConfig("./randomized_jfreechart_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(jfreechart_manual);
		randomizedFinder.setTrialNum(1000);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testJFreeChart_1() {
		Log.logConfig("./tmp_jfreechart_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(jfreechart_tmp);
		randomizedFinder.setTrialNum(0);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testXMLSecurity_manual() {
		Log.logConfig("./randomized_xmlsecurity_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(this.xmlSecurityFile_manual);
		randomizedFinder.setTrialNum(shuffle_times);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testCrystal_manual() {
		Log.logConfig("./randomized_crystal_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(this.crystalFile_manual);
		randomizedFinder.setTrialNum(shuffle_times);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	//6 in total:
	//
//	 org.joda.time.TestPeriodType.testForFields4
//	    org.joda.time.TestDateMidnight_Basics.testWithZoneRetainFields_DateTimeZone
//	    org.joda.time.TestDateTime_Basics.testToDateTime_DateTimeZone
//	    org.joda.time.TestDateTime_Basics.testWithZoneRetainFields_DateTimeZone
//	    org.joda.time.TestDateTimeComparator.testMillis
//	    org.joda.time.TestDateTimeUtils.testOffsetMillisToZero
	public void testJodaTime_manual() {
		Log.logConfig("./randomized_jodatime_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(this.jodatimeFile_manual);
		randomizedFinder.setTrialNum(shuffle_times);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testSynoptic_manual() {
		Log.logConfig("./randomized_synoptic_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(this.synopticFile_manual);
		randomizedFinder.setTrialNum(shuffle_times);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	//below is for automated generated tests
	public void testXMLSecurity_auto() {
		Log.logConfig("./randomized_xmlsecurity_auto_tests_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(this.xmlSecurityFile_auto);
		randomizedFinder.setTrialNum(shuffle_times);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	
	
	public void testJodaTime_auto() {
		Log.logConfig("./randomized_jodatime_auto_tests_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(jodatimeFile_auto);
		randomizedFinder.setTrialNum(100);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testSynoptic_auto() {
		Log.logConfig("./randomized_synoptic_auto_tests_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(synopticFile_auto);
		randomizedFinder.setTrialNum(shuffle_times);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	public void testCrystal_auto() {
		Log.logConfig("./randomized_crystal_auto_tests_results.txt");
		RandomizedDependentTestFinder.verbose = true;
		RandomizedDependentTestFinder randomizedFinder = new RandomizedDependentTestFinder(crystalFile_auto);
		randomizedFinder.setTrialNum(shuffle_times);
		Set<String> dts = randomizedFinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
	//for evaluation
	public static void main(String[] args) {
		
		if(args.length == 1) {
			try {
			    shuffle_times = Integer.parseInt(args[0]);
			    System.out.println("Setting shuffle times to: " + shuffle_times);
			} catch (Throwable e) {
				System.out.println("It should be an integer, but " + args[0] + " is provided.");
				return;
			}
		}
		
		TestRandomizedDependentTestFinder finder = new TestRandomizedDependentTestFinder();
		
		//manual tests
		finder.testJodaTime_manual();
		finder.testXMLSecurity_manual();
		finder.testCrystal_manual();
		finder.testSynoptic_manual();
		
		finder.testXMLSecurity_auto();
		finder.testCrystal_auto();
		finder.testSynoptic_auto();
	}
}
