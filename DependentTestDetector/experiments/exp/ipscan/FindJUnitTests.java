/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Wing Lam
 */
package exp.ipscan;

import java.io.IOException;
import java.util.zip.ZipException;

import junit.framework.TestCase;

import edu.washington.cs.dt.tools.UnitTestFinder;
import edu.washington.cs.dt.util.Log;

public class FindJUnitTests extends TestCase {
	
	public void testFindUnitTests() throws ZipException, ClassNotFoundException, IOException {
		Log.logConfig("./ipscan-findtests-log.txt");
		/**
		 * Make sure all jars under folder: experiments-subjects/ipscan (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		String[] args = new String[]{
				"--pathOrJarFile=./experiments-subjects/ipscan/ipscan.jar",
				"--outputFileName=./experiments-subjects/ipscan/ipscan-unit-tests.txt"
		};
		UnitTestFinder.main(args);
	}
	
}