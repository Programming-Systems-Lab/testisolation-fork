/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Wing Lam
 */
package exp.jabref;

import java.io.IOException;
import java.util.zip.ZipException;

import junit.framework.TestCase;

import edu.washington.cs.dt.tools.UnitTestFinder;
import edu.washington.cs.dt.util.Log;

public class FindJUnitTests extends TestCase {
	
	public void testFindUnitTests() throws ZipException, ClassNotFoundException, IOException {
		Log.logConfig("./jabref-findtests-log.txt");
		/**
		 * Make sure all jars under folder: experiments-subjects/jabref (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		//jabref-original.jar refers to an original version of jabref
		//jabref-commented.jar refers to an version of jabref that has certain tests known to hang commented out
		String[] args = new String[]{
				"--pathOrJarFile=./experiments-subjects/jabref/jabref-commented.jar",
				"--outputFileName=./experiments-subjects/jabref/jabref-unit-tests-commented.txt"
		};
		UnitTestFinder.main(args);
	}
	
}