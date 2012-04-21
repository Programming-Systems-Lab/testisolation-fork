package exp.hibernate;

import java.io.IOException;
import java.util.zip.ZipException;

import junit.framework.TestCase;

import edu.washington.cs.dt.tools.UnitTestFinder;
import edu.washington.cs.dt.util.Log;

public class FindJUnitTests extends TestCase {
	
	public void testFindUnitTests() throws ZipException, ClassNotFoundException, IOException {
		Log.logConfig("./hibernate-findtests-log.txt");
		/**
		 * Make sure all jars under folder: experiments-subjects/hibernate (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		String[] args = new String[]{
				"--pathOrJarFile=./experiments-subjects/hibernate/hibernate.jar",
				"--junit4=true",
				"--outputFileName=./experiments-subjects/hibernate/hibernate-unit-tests.txt"
		};
		UnitTestFinder.main(args);
	}
	
}