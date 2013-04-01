/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
		for(String fullTestName : tests) {
			long start = System.currentTimeMillis();
			/*check the results*/
			String result = null;
			//			String stackTrace = TestExecUtils.noStackTrace;
			String fullStackTrace = TestExecUtils.noStackTrace;

			final JUnitTestExecutor executor = new JUnitTestExecutor(fullTestName);
			executor.executeWithJUnit4Runner();		
			System.out.println(Globals.stdoutProgressPrefix);
			System.out.flush();
			result = executor.getResult();
			//				stackTrace = executor.getStackTrace();
			fullStackTrace = executor.getFullStackTrace();

			sb.append(Globals.stdoutPrefix);
			sb.append(fullTestName);
			sb.append(TestExecUtils.testResultSep);
			sb.append(result);
			sb.append(TestExecUtils.testResultSep);
			sb.append(System.currentTimeMillis() - start);			
			sb.append(TestExecUtils.resultExcepSep);
			//			sb.append(stackTrace);
			sb.append(fullStackTrace);
			sb.append(Globals.lineSep);
		}
		
		System.out.println(sb.toString());
	}
}
