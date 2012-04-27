/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package exp.hibernate;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class RunHibernateTests extends TestCase {
	
	public void testRunInIsolation() {
		Log.logConfig("./log-hibernate.txt");
		String inputTestFile = "./experiments-subjects/hibernate/hibernate-unit-tests.txt";
		/**
		 * Make sure all jars under folder: experiments-subjects/hibernate (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		Main.main(new String[]{"--minimize=false", "--isolate",
				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
	}
	
}