package edu.washington.cs.dt.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.dt.RESULT;

import plume.Option;


public class TestExecUtils {
	
	public static String sep = "#";
	
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
	public static Map<String, RESULT> executeTestsInFreshJVM(String classPath, String outputFile, List<String> tests) {
		
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
		
		Map<String, RESULT> testResults = parseTestResults(outputFile);
		
		Utils.checkTrue(tests.size() == testResults.size(), "Test num not equal.");
		
		return testResults;
	}
	
	public static Map<String, RESULT> parseTestResults(String outputFile) {
		Map<String, RESULT> ret = new LinkedHashMap<String, RESULT>();
		
		List<String> lines = Files.readWholeNoExp(outputFile);
		
		for(String line : lines) {
			int sepIndex = line.indexOf(TestExecUtils.sep);
			String testCase = line.substring(0, sepIndex);
			String result = line.substring(sepIndex + TestExecUtils.sep.length());
			if(result.equals(RESULT.PASS.name())) {
				ret.put(testCase, RESULT.PASS);
			} else if (result.equals(RESULT.FAILURE.name())) {
				ret.put(testCase, RESULT.FAILURE);
			} else if (result.equals(RESULT.ERROR.name())) {
				ret.put(testCase, RESULT.ERROR);
			} else {
				throw new RuntimeException("Unknown result: " + result);
			}
		}
		
		return ret;
	}
	
}