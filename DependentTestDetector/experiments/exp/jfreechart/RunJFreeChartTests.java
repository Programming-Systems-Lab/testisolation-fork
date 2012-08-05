/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package exp.jfreechart;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.tools.RunTestInFixedOrder;
import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class RunJFreeChartTests extends TestCase {
	String inputTestFile = "./experiments-subjects/jfreechart/jfreechart-junittests.txt";
	
//	public void testRunInIsolation() {
//		Log.logConfig("./log-jfreechart.txt");
//		//please update the whole workspace, I add a folder containing necessary jars
//		//for the experiments.
//		//make sure the jars under ./experiments-subjects/jfreechart are added to classpath
//		Main.main(new String[]{"--minimize=false", "--isolate",
//				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
//	}
	
	public void testRunTestsInFixedOrder() {
		String[] args = new String[] {
				"--testFile=" + inputTestFile,
				"--outputFile=./experiments-subjects/jfreechart/dt-jfreechart-fixedorder.txt"
		};
		RunTestInFixedOrder.main(args);
	}
}