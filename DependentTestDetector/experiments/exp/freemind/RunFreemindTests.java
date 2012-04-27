/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package exp.freemind;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class RunFreemindTests extends TestCase {
	public void testRunInIsolation() {
		Log.logConfig("./log-freemind.txt");
		String inputTestFile = "./experiments-subjects/freemind/freemind-unit-tests.txt";
		/**
		 * Make sure all jars under folder: experiments-subjects/freemind (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		Main.main(new String[]{"--minimize=false", "--isolate",
				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
	}
}
