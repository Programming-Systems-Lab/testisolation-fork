/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Wing Lam
 */
package exp.squirrel;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class RunSquirrelTests extends TestCase {
	String workspaceDir = "/home/dasxce/workspace/DependentTestDetector/DependentTestDetector";
	String inputTestFile = workspaceDir + "/experiments-subjects/squirrel/squirrel-unit-tests.txt";
	
	public void testRunInIsolation() {
		Log.logConfig("./log-squirrel.txt");
		/**
		 * Make sure all jars under folder: experiments-subjects/squirrel (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		Main.main(new String[]{"--minimize=false", "--isolate",
				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
	}
	
	public void testRunInPairwise() {
		Log.logConfig("./log-squirrel.txt");
		/**
		 * Make sure all jars under folder: experiments-subjects/squirrel (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		Main.main(new String[]{"--minimize=false",
				"--combination", "--k=2",
				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
	}
}