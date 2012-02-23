package edu.washington.cs.dt.util;

import java.io.IOException;

import junit.framework.TestResult;
import junit.textui.TestRunner;

public class TestRunnerWrapper {
	
	/*
	 * args[0]: the result output file
	 * args[1 : ]: the tests to be executed
	 * */
	public static void main(String[] args) throws IOException {
		/*parse the argument*/
		String outputFile = args[0];
		String[] tests = new String[args.length - 1];
		for(int index = 1; index < args.length; index++) {
			tests[index - 1] = args[index];
		}
		/*create the StringBuilder to output results*/
		StringBuilder sb = new StringBuilder();
		/*create a test runner*/
		TestRunner aTestRunner= new TestRunner();
		for(String test : tests) {
			String[] junitArgs = new String[]{"-m", test};
			/*check the results*/
			String result = null;
			try {
				TestResult r = aTestRunner.start(junitArgs);
				if(r.wasSuccessful()) {
					result = RESULT.PASS.name();
				} else {
					if(r.errorCount() > 0) {
						result = RESULT.ERROR.name();
					}
					if(r.failureCount() > 0) {
						result = RESULT.FAILURE.name();
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			sb.append(test + TestExecUtils.sep + result);
			sb.append(Globals.lineSep);
		}
		Files.writeToFile(sb.toString(), outputFile);
	}
	
}