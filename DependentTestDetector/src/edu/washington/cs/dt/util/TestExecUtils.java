package edu.washington.cs.dt.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestFailure;

import edu.washington.cs.dt.OneTestExecResult;
import edu.washington.cs.dt.RESULT;

import plume.Option;


public class TestExecUtils {
	
	public static String testResultSep = "#";
	
	public static String resultExcepSep = "%#%#";
	
	public static String noStackTrace = "NO_STACK_TRACE_FOR_A_PASSING_TEST";
	
	@Option("The temp file to store all tests to execute")
	public static String testsfile = "./tmptestfiles.txt";
	
	@Option("The min number of tests when using ./tmptestfiles")
	public static int threshhold = 120;
	
	/*
	 * Executes a list of tests in order by launching a fresh JVM, and
	 * returns the result of each test.
	 * 
	 * The test is in the form of packageName.className.methodName
	 * */
	public static Map<String, OneTestExecResult> executeTestsInFreshJVM(String classPath, String outputFile, List<String> tests) {
		
		List<String> commandList = new LinkedList<String>();
		commandList.add("java");
		commandList.add("-cp");
		commandList.add(classPath + Globals.pathSep + System.getProperties().getProperty("java.class.path", null));
		
		if(tests.size() < threshhold) {
		    commandList.add("edu.washington.cs.dt.util.TestRunnerWrapper");
		    commandList.add(outputFile);
		    commandList.addAll(tests);
		} else {
			Files.createIfNotExistNoExp(testsfile);
			Files.writeToFileWithNoExp(tests, testsfile);
			
			commandList.add("edu.washington.cs.dt.util.TestRunnerWrapperFileInputs");
		    commandList.add(outputFile);
		    commandList.add(testsfile);
		}
		
		String[] args = commandList.toArray(new String[0]);
		
		Command.exec(args);
		
		Map<String, OneTestExecResult> testResults = parseTestResults(outputFile);
		
		Utils.checkTrue(tests.size() == testResults.size(), "Test num not equal.");
		
		return testResults;
	}
	
	public static Map<String, OneTestExecResult> parseTestResults(String outputFile) {
		Map<String, OneTestExecResult> ret = new LinkedHashMap<String, OneTestExecResult>();
		
		List<String> lines = Files.readWholeNoExp(outputFile);
		
		for(String line : lines) {
			int resultSepIndex = line.indexOf(TestExecUtils.testResultSep);
			int excepSepIndex = line.indexOf(TestExecUtils.resultExcepSep);
			Utils.checkTrue(resultSepIndex != -1, "resultSepIndex != -1");
			Utils.checkTrue(excepSepIndex != -1, "excepSepIndex != -1");
			
			String testCase = line.substring(0, resultSepIndex);
			String result = line.substring(resultSepIndex + TestExecUtils.testResultSep.length(), excepSepIndex);
			String stacktrace = line.substring(excepSepIndex + TestExecUtils.resultExcepSep.length(), line.length());
			if(result.equals(RESULT.PASS.name())) {
				OneTestExecResult r = new OneTestExecResult(RESULT.PASS, stacktrace);
				ret.put(testCase, r);
			} else if (result.equals(RESULT.FAILURE.name())) {
				OneTestExecResult r = new OneTestExecResult(RESULT.FAILURE, stacktrace);
				ret.put(testCase, r);
			} else if (result.equals(RESULT.ERROR.name())) {
				OneTestExecResult r = new OneTestExecResult(RESULT.ERROR, stacktrace);
				ret.put(testCase, r);
			} else {
				throw new RuntimeException("Unknown result: " + result);
			}
		}
		
		return ret;
	}
	
	public static final String JUNIT_ASSERT = "junit.framework.Assert";
	
	public static String flatStackTrace(TestFailure failure, String excludeRegex) {
		Pattern p = Pattern.compile(excludeRegex);
		Throwable t = failure.thrownException();
		String[] stackTraces = extractStackTraces(t);
		String flatString = flatStrings(stackTraces, p, JUNIT_ASSERT);
		return flatString;
	}
	
	public static String flatStrings(String[] strs, Pattern excludeRegex, String exceptedPrefix) {
		StringBuilder sb = new StringBuilder();
		for(String str : strs) {
			if(shouldExclude(str, excludeRegex, exceptedPrefix)) {
				continue;
			}
			sb.append(str);
			sb.append(" - ");
		}
		return sb.toString();
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