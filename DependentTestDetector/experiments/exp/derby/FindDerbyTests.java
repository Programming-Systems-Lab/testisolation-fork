/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package exp.derby;

import java.io.IOException;
import java.util.zip.ZipException;

import junit.framework.TestCase;

import edu.washington.cs.dt.tools.UnitTestFinder;
import edu.washington.cs.dt.util.Log;

public class FindDerbyTests extends TestCase {

	public void testFindAllTests() throws ZipException, ClassNotFoundException, IOException {
		Log.logConfig("./derby-findtests-log.txt");
		/**
		 * Make sure the path is added to the classpath
		 * */
		String classPath = "D:\\research\\testisolation\\subject-programs\\derby\\db-derby-10.9.1.0-src\\db-derby-10.9.1.0-src\\classes";
		String[] args = new String[]{
				"--pathOrJarFile=" + classPath,
				"--junit3and4=true",
				"--outputFileName=./experiments-subjects/derby/derby-unit-tests.txt"
		};
		UnitTestFinder.main(args);
	}
}