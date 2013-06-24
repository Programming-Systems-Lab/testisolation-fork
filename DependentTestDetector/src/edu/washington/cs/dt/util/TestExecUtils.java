/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestFailure;
import plume.Option;
import edu.washington.cs.dt.OneTestExecResult;
import edu.washington.cs.dt.RESULT;
import edu.washington.cs.dt.main.Main;


public class TestExecUtils {
	
	public static String testResultSep = "#";
	
	public static String resultExcepSep = "%#%#";
	
	public static String noStackTrace = "NO_STACK_TRACE_FOR_A_PASSING_TEST";
	
	@Option("The temp file to store all tests to execute")
	public static String testsfile = "./tmptestfiles.txt";
	

	
	public static Map<String, OneTestExecResult> executeTestsInFreshJVM(String classPath, String outputFile,
			List<String> tests, ProgressCallback progressCallback) {
		
		List<String> commandList = new LinkedList<String>();
		commandList.add("java");
		commandList.add("-cp");
		commandList.add(classPath + Globals.pathSep + System.getProperties().getProperty("java.class.path", null));
		commandList.add("-Xmx2G");
		commandList.add("edu.washington.cs.dt.seperatejvm.TestRunner");
		
		String[] args = commandList.toArray(new String[0]);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		MonitoringPrintStream ps   = new MonitoringPrintStream(out, true);
		ps.setCallback(progressCallback);
		String input = "";
		for (String test : tests) {
			input += test  + "\n";
		}
		Command.exec(args, ps, input);
		ps.close();

		try {
			out.close();
			Files.createAndWriteFile(new File(outputFile), ps.getFilteredLines());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, OneTestExecResult> testResults = parseTestResults(outputFile);
		
		Utils.checkTrue(tests.size() == testResults.size(), "Test num not equal.");
		
		return testResults;
	}
	
	static Map<String, OneTestExecResult> parseTestResults(String outputFile) {
		Map<String, OneTestExecResult> ret = new LinkedHashMap<String, OneTestExecResult>();
		
		List<String> lines = Files.readWholeNoExp(outputFile);
		Pattern p = Pattern.compile( ("(.*)" + testResultSep + "(\\w+)" + testResultSep + "(\\d+)" + resultExcepSep + "(.*)"));
		for(String line : lines) {
			Matcher m = p.matcher(line);
			Utils.checkTrue(m.find(), "Line did not match format");
		
			String testCase = m.group(1);
			String result = m.group(2);
			long time = Long.parseLong(m.group(3));
			String fullStacktrace = m.group(4);
			
			if(result.equals(RESULT.PASS.name())) {
				OneTestExecResult r = new OneTestExecResult(RESULT.PASS, fullStacktrace, time);
				ret.put(testCase, r);
			} else if (result.equals(RESULT.FAILURE.name())) {
				OneTestExecResult r = new OneTestExecResult(RESULT.FAILURE, fullStacktrace, time);
				ret.put(testCase, r);
			} else if (result.equals(RESULT.ERROR.name())) {
				OneTestExecResult r = new OneTestExecResult(RESULT.ERROR, fullStacktrace, time);
				ret.put(testCase, r);
			} else {
				throw new RuntimeException("Unknown result: " + result);
			}
		}
		
		return ret;
		}
	
	/*
	 * Executes a list of tests in order by launching a fresh JVM, and
	 * returns the result of each test.
	 * 
	 * The test is in the form of packageName.className.methodName
	 * */
	public static Map<String, OneTestExecResult> executeTestsInFreshJVM(String classPath, String outputFile, List<String> tests) {
		return executeTestsInFreshJVM(classPath, outputFile, tests, null);
	}
	
	public static final String JUNIT_ASSERT = "junit.framework.Assert";
	
	public static String flatStackTrace(TestFailure failure, String excludeRegex) {
		Pattern p = Pattern.compile(excludeRegex);
		Throwable t = failure.thrownException();
		String[] stackTraces = extractStackTraces(t);
		String flatString = flatStrings(stackTraces, p, JUNIT_ASSERT);
		return flatString;
	}
	
	public static String stackTraceSep = " - ";
	
	public static String flatStrings(String[] strs, Pattern excludeRegex, String exceptedPrefix) {
		StringBuilder sb = new StringBuilder();
		for(String str : strs) {
			if(shouldExclude(str, excludeRegex, exceptedPrefix)) {
				continue;
			}
			if(str.startsWith("edu.washington.cs.dt")) {
				continue;
			}
			sb.append(str);
			sb.append(stackTraceSep);
		}
		return sb.toString();
	}
	
	public static String flatStrings(String[] strs) {
		StringBuilder sb = new StringBuilder();
		for(String str : strs) {
			sb.append(str);
			sb.append(stackTraceSep);
		}
		return sb.toString();
	}
	
	public static String flatFilteredStackTraces(String fullStackTrace) {
		String[] elements = fullStackTrace.split(stackTraceSep);
		Pattern p = Pattern.compile(Main.excludeRegex);
		String flatString = TestExecUtils.flatStrings(elements, p, TestExecUtils.JUNIT_ASSERT);
		return flatString;
	}
	
	public static String[] extractStackTraces(Throwable t) {
		String[] traces = new String[t.getStackTrace().length];
		for(int i = 0; i < t.getStackTrace().length; i++) {
			traces[i] = t.getStackTrace()[i].toString().trim();
		}
		return traces;
	}
	
	public static boolean shouldExclude(String target, Pattern p, String exceptedPrefix) {
		Matcher m = p.matcher(target);
		if( m.find() && !(target.startsWith(exceptedPrefix))) {
			return true;
		}
		return false;
	}

	
}