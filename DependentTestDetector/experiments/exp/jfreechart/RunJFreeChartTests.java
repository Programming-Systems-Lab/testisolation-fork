package exp.jfreechart;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.Log;
import junit.framework.TestCase;

public class RunJFreeChartTests extends TestCase {
	
	public void testRunInIsolation() {
		Log.logConfig("./log-jfreechart.txt");
		String inputTestFile = "./experiments-subjects/jfreechart/jfreechart-junittests.txt";
		//please update the whole workspace, I add a folder containing necessary jars
		//for the experiments.
		//make sure the jars under ./experiments-subjects/jfreechart are added to classpath
		Main.main(new String[]{"--minimize=false", "--isolate",
				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
	}
	
}