/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package exp.synoptic;

import java.util.Collection;
import java.util.Set;

import junit.framework.TestCase;
import edu.washington.cs.dt.ClusterAnalyzer;
import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.tools.TestAccessingFieldsCollector;
import edu.washington.cs.dt.util.Log;

public class RunSynopticTests extends TestCase {
	public void testRunInIsolation() {
		Log.logConfig("./log-synoptic.txt");
		String inputTestFile = "./experiments-subjects/synoptic/synoptic-unit-tests.txt";
		/**
		 * Make sure all jars under folder: experiments-subjects/synoptic (including those
		 * in lib) are added to classpath  before running this test.
		 * */
		Main.main(new String[]{"--minimize=false", "--isolate",
				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
		
//		Main.main(new String[]{"--minimize=false", "--combination", "--k=2",
//				"--tests=" + inputTestFile, "--showProgress", "--printstacktrace"});
	}
	
	public void testCollectFieldAccesses() {
		String inputTestFile = "./experiments-subjects/synoptic/synoptic-unit-tests.txt";
		String[] args = new String[]{
				"--testFile", inputTestFile};
		TestAccessingFieldsCollector.main(args);
	}
	
	public void testFindCluster() {
		String dir = "./output";
		ClusterAnalyzer analyzer = new ClusterAnalyzer(dir);
		Collection<Set<String>> colls = analyzer.generateClusters();
		analyzer.showBasicInfo(colls);
	}
}