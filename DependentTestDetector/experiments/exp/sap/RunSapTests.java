/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Wing Lam
 */
package exp.sap;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.tools.RunTestInFixedOrder;
import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class RunSapTests extends TestCase {
	String inputTestFile = "./experiments-subjects/sap/sap-unit-tests.txt";
	
//	public void testRunInIsolation() {
//		Log.logConfig("./log-sap.txt");
//		/**
//		 * Make sure all jars under folder: experiments-subjects/sap (including those
//		 * in lib) are added to classpath  before running this test.
//		 * */
//		Main.main(new String[]{"--minimize=false", "--isolate",
//				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
//	}
	
//	public void testRunInPairwise() {
//		Log.logConfig("./log-sap.txt");
//		/**
//		 * Make sure all jars under folder: experiments-subjects/sap (including those
//		 * in lib) are added to classpath  before running this test.
//		 * */
//		Main.main(new String[]{"--minimize=false",
//				"--combination", "--k=2",
//				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
//	}
	
	public void testRunTestsInFixedOrder() {
		String[] args = new String[] {
				"--testFile=" + inputTestFile,
				"--outputFile=./experiments-subjects/sap/dt-sap-fixedorder.txt"
		};
		RunTestInFixedOrder.main(args);
	}
}