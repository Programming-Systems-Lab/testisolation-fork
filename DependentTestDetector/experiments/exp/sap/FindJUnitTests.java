/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Wing Lam
 */
package exp.sap;

import java.io.IOException;
import java.util.zip.ZipException;

import junit.framework.TestCase;

import edu.washington.cs.dt.tools.UnitTestFinder;
import edu.washington.cs.dt.util.Log;

public class FindJUnitTests extends TestCase {
	
	public void testFindUnitTests() throws ZipException, ClassNotFoundException, IOException {
		Log.logConfig("./sap-findtests-log.txt");
		/**
		 * Make sure all jars under folder: experiments-subjects/sap (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		String[] args = new String[]{
				"--pathOrJarFile=./experiments-subjects/sap/sap.jar",
				"--outputFileName=./experiments-subjects/sap/sap-unit-tests.txt"
		};
		UnitTestFinder.main(args);
	}
	
}