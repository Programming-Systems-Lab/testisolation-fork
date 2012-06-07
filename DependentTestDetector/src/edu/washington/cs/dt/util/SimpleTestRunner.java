/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.util;

import junit.framework.TestResult;
import junit.textui.TestRunner;

public class SimpleTestRunner {

	public static void main(String[] args) {
		Utils.checkTrue(args != null && args.length == 1, "The args must be not null and 1");
		/*parse the argument*/
		String fullTestName = args[0];
		/*create a test runner*/
		TestRunner aTestRunner= new TestRunner();
			boolean useJUnit4 = CodeUtils.useJUnit4(fullTestName);
			if (useJUnit4) {
				JUnitTestExecutor executor = new JUnitTestExecutor(fullTestName);
				executor.executeJUnit4();
				System.out.println("executing: ? " + fullTestName + ", successfully? " + executor.getResult());
			} else {
				try {
					String[] junitArgs = new String[]{"-m", fullTestName};
					// System.out.println(Utils.convertArrayToFlatString(junitArgs));
					TestResult r = aTestRunner.start(junitArgs);
					System.out.println("executing: ? " + fullTestName + ", successfully? " + r.wasSuccessful());
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
    }
}