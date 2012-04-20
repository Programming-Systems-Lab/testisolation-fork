package edu.washington.cs.dt.util;

import java.io.File;
import java.io.IOException;

import edu.washington.cs.dt.RESULT;
import edu.washington.cs.dt.main.Main;

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
			boolean useJUnit4 = CodeUtils.useJUnit4(fullTestName);
			/*check the results*/
			String result = null;
			String stackTrace = TestExecUtils.noStackTrace;
			if (useJUnit4) {
				JUnitTestExecutor executor = new JUnitTestExecutor(fullTestName);
				executor.executeJUnit4();
				result = executor.getResult();
				stackTrace = executor.getStackTrace();
			} else {
				try {
					String[] junitArgs = new String[]{"-m", fullTestName};
					// System.out.println(Utils.convertArrayToFlatString(junitArgs));
					TestResult r = aTestRunner.start(junitArgs);
					if (r.wasSuccessful()) {
						result = RESULT.PASS.name();
					} else {
						if (r.errorCount() > 0) {
							Utils.checkTrue(r.errorCount() == 1,
									"Only execute 1 test: " + fullTestName + ", two errors: "
											+ CodeUtils.flattenFailrues(r.errors()));
							result = RESULT.ERROR.name();
							stackTrace = TestExecUtils.flatStackTrace(r.errors().nextElement(), Main.excludeRegex);
						}
						if (r.failureCount() > 0) {
							Utils.checkTrue(r.failureCount() == 1,
									"Only execute 1 test: " + fullTestName + ", two failures: "
											+ CodeUtils.flattenFailrues(r.failures()));
							result = RESULT.FAILURE.name();
							stackTrace = TestExecUtils.flatStackTrace(r.failures().nextElement(),
									Main.excludeRegex);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			
			sb.append(fullTestName);
			sb.append(TestExecUtils.testResultSep);
			sb.append(result);
			sb.append(TestExecUtils.resultExcepSep);
			sb.append(stackTrace);
			sb.append(Globals.lineSep);
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
	}
	
}