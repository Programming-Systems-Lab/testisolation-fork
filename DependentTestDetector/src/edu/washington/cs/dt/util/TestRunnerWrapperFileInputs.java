package edu.washington.cs.dt.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import edu.washington.cs.dt.RESULT;
import edu.washington.cs.dt.main.Main;

import junit.framework.TestResult;
import junit.textui.TestRunner;

/**
 * Beaware, also need to change TestRunnerWrapper
 * */

public class TestRunnerWrapperFileInputs {
	/*
	 * args[0]: the result output file
	 * args[1]: a file containing all tests
	 * */
	public static void main(String[] args) throws IOException {
		if(args.length != 2) {
			System.err.println("The arg number must be 2: args[0] " +
					"is the output file, args[1] is the file containing all unit tests.");
			System.exit(2);
		}
		/*parse the argument*/
		String outputFile = args[0];
		List<String> content = Files.readWholeNoExp(args[1]);
		List<String> tests = new LinkedList<String>();
		for(String line : content) {
			if(!line.trim().equals("")) {
				tests.add(line.trim());
			}
		}
		
		/*create the StringBuilder to output results*/
		StringBuilder sb = new StringBuilder();
		/*create a test runner*/
		TestRunner aTestRunner= new TestRunner();
		for(String test : tests) {
			String[] junitArgs = new String[]{"-m", test};
			/*check the results*/
			String result = null;
			String stackTrace = TestExecUtils.noStackTrace;
			try {
//				System.out.println(Utils.convertArrayToFlatString(junitArgs));
				TestResult r = aTestRunner.start(junitArgs);
				if(r.wasSuccessful()) {
					result = RESULT.PASS.name();
				} else {
					if(r.errorCount() > 0) {
						Utils.checkTrue(r.errorCount() == 1, "Only execute 1 test");
						result = RESULT.ERROR.name();
						stackTrace = TestExecUtils.flatStackTrace(r.errors().nextElement(), Main.excludeRegex);
					}
					if(r.failureCount() > 0) {
						Utils.checkTrue(r.failureCount() == 1, "Only execute 1 test");
						result = RESULT.FAILURE.name();
						stackTrace = TestExecUtils.flatStackTrace(r.failures().nextElement(), Main.excludeRegex);
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			sb.append(test);
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
