package edu.washington.cs.dt.tools;

import java.util.Set;

import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class TestDependenceAwareTestFinder extends TestCase {
	
	public void testXMLSecurityPairwise_manual() {
		runTests(TestRandomizedDependentTestFinder.xmlSecurityFile_manual, TestReader.xml_manual_pairwise,
				"./dependence-aware_2_xmlsecurity_manual.txt", 2);
	}

	public void runTests(String testFile, String folderName, String logFileName, int k) {
		Log.logConfig(logFileName);
		DependenceAwareDependentTestFinder.verbose = true;
		DependenceAwareDependentTestFinder dafinder
		    = new DependenceAwareDependentTestFinder(testFile, folderName, k);
		Set<String> dts = dafinder.findDependentTests();
		for(String t : dts) {
			System.out.println("    " + t);
		}
	}
	
}
