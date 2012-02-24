package edu.washington.cs.dt.main;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import edu.washington.cs.dt.DependentTestIdentifier;
import edu.washington.cs.dt.TestExecResultsDelta;
import edu.washington.cs.dt.tools.UnitTestFinder;
import edu.washington.cs.dt.util.Files;
import edu.washington.cs.dt.util.Utils;
import plume.Option;
import plume.Options;

/*
 * The entry point
 * */
public class Main {
	
	/**
	 * Basic workflow:
	 * input: a file containing all junit tests
	 *        specify classpath
	 *        specify tmpOutputFile
	 * output: a report output file (default by print)
	 * */
   
	@Option("Show all options")
	public static boolean help = false;
	
	@Option("The classpath for executing the tests")
	public static String classpath = "";
	
	@Option("A file containing all tests to be executeds")
	public static String tests = null;
	
	@Option("A file containing all intermediate temp results")
	public static String tmpfile = "." + File.separator + "tmpfile.txt";
	
	@Option("A file containing the final report")
	public static String report = "." + File.separator + "report.txt";
	
	@Option("Use delta debugging to minize tests")
	public static boolean minimize = false;
	
	@Option("Enable cache to improve performance in minimzing tests")
	public static boolean enablecache = true;
	
	@Option("Remove redundant reports after minimizing dependent tests")
	public static boolean removeredundancy = true;
	
	@Option("Execute tests in isolation to check the results")
	public static boolean isolate = false;
	
	@Option("Execute tests in a reverse order to check the results")
	public static boolean reverse = false;
	
	@Option("Execute tests in all possible combinations")
	public static boolean combination = false;
	
	@Option("The length of each combination")
	public static int k = -1;
	
	/* see lanuchDetector for details about how to initialize the detector*/
	public static void main(String[] args) {
		new Main().nonStaticMain(args);
	}
	
	/**
	 * methods for processing the input arguments, and launching the tool
	 * */
	private void nonStaticMain(String[] args) {
		this.parse_and_validate_args(args);
		lanchDetector();
	}
	
	private void parse_and_validate_args(String[] args) {
		Options options = new Options("Dependent Unit Tests Detector usage: ", Main.class);
	    String[] file_args = options.parse_or_usage(args);
	    if(file_args.length != 0) {
	        Utils.flushToStd(file_args);
	        System.exit(1);
	    }
	    if(help) {
	    	Utils.flushToStd(options.usage());
	        System.exit(1);
	    }
	    List<String> errorMsg = new LinkedList<String>();
	    if(tests == null) {
	    	errorMsg.add("You must specify either a file containing all tests via -tests");
	    }
	    int chosenOptions = 0;
	    if(isolate) { chosenOptions++; }
	    if(reverse) { chosenOptions++; }
	    if(combination) {
	    	if(k < 1) {
	    		errorMsg.add("The option k must be specified via --k, when --combination is specified");
	    	}
	    	chosenOptions++;
	    }
	    
	    if(chosenOptions == 0) {
	    	errorMsg.add("You must chose one option for detecting dependent tests: --isolate, --reverse, or --combination.");
	    }
	    if(chosenOptions > 1) {
	    	errorMsg.add("You can only chose one option for detecting dependent tests: --isolate, --reverse, or --combination.");
	    }
	    
	    if(!errorMsg.isEmpty()) {
	    	Utils.flushToStd(errorMsg.toArray(new String[0]));
	    	Utils.flushToStd(options.usage());
	        System.exit(1);
	    }
	}
	
	private void lanchDetector() {
		List<String> allTests = Files.readWholeNoExp(tests);
		DependentTestIdentifier detector = new DependentTestIdentifier(allTests);
		detector.setClasspath(classpath);
		detector.setTmpOutputFile(tmpfile);
		detector.setMinimize(minimize);
		//keep all test execution differences
		List<TestExecResultsDelta> diffs = new LinkedList<TestExecResultsDelta>();
		if(isolate) {
		    diffs = detector.findDependenceForIsolation();
		} else if (reverse) {
			diffs = detector.findDependenceForReverse();
		} else if (combination) {
			diffs = detector.findDependenceForCombination(k);
		} else {
			throw new RuntimeException("Bugs in argument processing.");
		}
		//write the result to file
		TestExecResultsDelta.writeToFile(diffs, report);
	}
}