/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package exp.findbugs;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.tools.RunTestInFixedOrder;
import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class RunFindbugsTests extends TestCase {
	String inputTestFile = "./experiments-subjects/findbugs/findbugs-unit-tests.txt";
	
//	public void testRunInIsolation() {
//		Log.logConfig("./log-findbugs.txt");
//		/**
//		 * Make sure all jars under folder: experiments-subjects/findbugs (including those
//		 * in lib) are added to classpath  before running this test.
//		 * 
//		 * NOTE: the plume.jar contains bcel code, please put it AFTER the
//		 *       bcel.jar in classpath.
//		 * */
//		Main.main(new String[]{"--minimize=false",
//				"--isolate",
//				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
//	}
	
//	public void testRunInPairwise() {
//		Log.logConfig("./log-findbugs.txt");
//		/**
//		 * Make sure all jars under folder: experiments-subjects/findbugs (including those
//		 * in lib) are added to classpath  before running this test.
//		 * 
//		 * NOTE: the plume.jar contains bcel code, please put it AFTER the
//		 *       bcel.jar in classpath.
//		 * */
//		Main.main(new String[]{"--minimize=false",
//				"--combination", "--k=2",
//				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
//	}
	
	public void testRunTestsInFixedOrder() {
		String[] args = new String[] {
				"--testFile=" + inputTestFile,
				"--outputFile=./experiments-subjects/findbugs/dt-findbugs-fixedorder.txt"
		};
		RunTestInFixedOrder.main(args);
	}
}
