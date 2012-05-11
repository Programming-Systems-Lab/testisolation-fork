/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package exp.findbugs;

import java.io.IOException;
import java.util.zip.ZipException;

import junit.framework.TestCase;

import edu.washington.cs.dt.tools.UnitTestFinder;
import edu.washington.cs.dt.util.Log;

public class FindJUnitTests extends TestCase {
	
	public void testFindUnitTests() throws ZipException, ClassNotFoundException, IOException {
		Log.logConfig("./findbugs-findtests-log.txt");
		/**
		 * Make sure all jars under folder: experiments-subjects/findbugs (including those
		 * in libs) are added to classpath  before running this test.
		 * 
		 * NOTE: the plume.jar contains bcel code, please put it AFTER the
		 *       bcel.jar in classpath.
		 * */
		String[] args = new String[]{
				"--pathOrJarFile=./experiments-subjects/findbugs/findbugs-2.0.1.jar",
				"--junit3and4=true",
				"--outputFileName=./experiments-subjects/findbugs/findbugs-unit-tests.txt"
		};
		UnitTestFinder.main(args);
	}
	
}