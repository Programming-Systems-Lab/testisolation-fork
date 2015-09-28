/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.util;

import java.io.File;
import java.io.IOException;

import edu.washington.cs.dt.RESULT;
import edu.washington.cs.dt.main.Main;

import junit.framework.TestFailure;
import junit.framework.TestResult;
import junit.textui.TestRunner;

/**
 * Beaware, also need to change TestRunnerWrapperFileInputs
 * */

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
		for(String fullTestName : tests) {
			String firstTestName = fullTestName;
			if (firstTestName.contains(" "))
				firstTestName = firstTestName.substring(0, firstTestName.indexOf(' '));
			long start = System.currentTimeMillis();
			boolean useJUnit4 = CodeUtils.useJUnit4(firstTestName);
			/*check the results*/
			String result = null;
			String fullStackTrace = TestExecUtils.noStackTrace;
			if (useJUnit4) {
				JUnitTestExecutor executor = new JUnitTestExecutor(fullTestName);
				executor.executeWithJUnit4Runner();
				System.out.println(Globals.stdoutProgressPrefix);
				System.out.flush();
				result = executor.getResult();
				fullStackTrace = executor.getFullStackTrace();
			} else {
				try {
					String[] junitArgs = null;
					int ntest = fullTestName.split(" ").length;
					if(fullTestName.contains(" "))
						junitArgs = new String[]{firstTestName.substring(0, firstTestName.lastIndexOf('.'))};
					else
						junitArgs = new String[]{"-m", fullTestName};
					TestResult r = aTestRunner.start(junitArgs);
					System.out.println(Globals.stdoutProgressPrefix);
					System.out.flush();	
					if (r.wasSuccessful()) {
						result = RESULT.PASS.name();
					} else {
						if (r.errorCount() > 0) {
							Utils.checkTrue(r.errorCount() <= ntest,
									"Only execute "+ntest+" test: " + fullTestName + ", "+r.errorCount()+" errors: "
											+ CodeUtils.flattenFailrues(r.errors()));
							result = RESULT.ERROR.name();
							TestFailure failure = r.errors().nextElement();
							fullStackTrace = TestExecUtils.flatStrings(TestExecUtils.extractStackTraces(failure.thrownException()));
						}
						if (r.failureCount() > 0) {
							Utils.checkTrue(r.failureCount() <= ntest,
									"Only execute "+ntest+" test: " + fullTestName + ", "+r.failureCount()+" failures: "
											+ CodeUtils.flattenFailrues(r.failures()));
							result = RESULT.FAILURE.name();
							TestFailure failure = r.failures().nextElement();

							fullStackTrace = TestExecUtils.flatStrings(TestExecUtils.extractStackTraces(failure.thrownException()));
						}
					}
				} catch (Exception e) {
					System.err.println("!!!!!BAILING!!!!");
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			
			sb.append(Globals.stdoutPrefix);
			sb.append(fullTestName);
			sb.append(TestExecUtils.testResultSep);
			sb.append(result);
			sb.append(TestExecUtils.testResultSep);
			sb.append(System.currentTimeMillis() - start);
			sb.append(TestExecUtils.resultExcepSep);
			sb.append(fullStackTrace);
			sb.append(Globals.lineSep);
//			System.out.println("OK done with " + fullTestName);
		}
		//if not exist, create it
		File f = new File(outputFile);
		if(!f.exists()) {
			File dir = f.getParentFile();
			boolean created = true;
			if(!dir.exists()) {
				created = dir.mkdirs();
			}
			created = created & f.createNewFile();
			if(!created) {
				throw new RuntimeException("Cannot create: " + outputFile);
			}
		}
		Files.writeToFile(sb.toString(), outputFile);
		System.out.println(sb.toString());
	}
}