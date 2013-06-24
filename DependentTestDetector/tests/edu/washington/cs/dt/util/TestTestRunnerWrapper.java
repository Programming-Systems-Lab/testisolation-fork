/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.util;

import java.io.IOException;
import java.util.Arrays;

import plume.LimitedSizeIntSet;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.seperatejvm.TestRunner;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestTestRunnerWrapper extends TestCase {
	
	public static Test suite() {
		return new TestSuite(TestTestRunnerWrapper.class);
	}
	
	public void testExe1() throws IOException {
		TestExecUtils.executeTestsInFreshJVM("", "./tmp.txt", Arrays.asList("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testX"));
	}
	
	public void testExe2() throws IOException {
		TestExecUtils.executeTestsInFreshJVM("", "./tmp.txt", Arrays.asList("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testZ"));
	}
	
	public void testExe3() throws IOException {
		TestExecUtils.executeTestsInFreshJVM("", "./tmp.txt", Arrays.asList("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testE"));
	}
	
	public void testExeAll() throws IOException {
		TestExecUtils.executeTestsInFreshJVM("", "./tmp.txt", Arrays.asList(
				"edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testX",
				"edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testY",
				"edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testE"));
		
	}

	
	
	
}
