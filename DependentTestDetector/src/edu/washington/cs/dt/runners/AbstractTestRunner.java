package edu.washington.cs.dt.runners;

import java.util.LinkedList;
import java.util.List;

import edu.washington.cs.dt.TestExecResults;
import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.Files;

public abstract class AbstractTestRunner {

	/* The input tests */
	protected final List<String> junitTestList;
	
	/*keep the classpath needs to run the tests, tmp output file to keep the
	 * intermediate results*/
	protected String classPath = null;
	protected String tmpOutputFile = null;
	
	/* Note that we use List here, since order matters*/
	public AbstractTestRunner(List<String> tests) {
		this.junitTestList = new LinkedList<String>();
		this.junitTestList.addAll(tests);
		this.classPath = System.getProperties().getProperty("java.class.path", null);
		tmpOutputFile = Main.tmpfile;
	}
	
	public AbstractTestRunner(String fileName) {
		this(Files.readWholeNoExp(fileName));
	}
	
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	
	public String getClassPath() {
		return this.classPath;
	}
	
	public void setTmpOutputFile(String fileName) {
		this.tmpOutputFile = fileName;
	}
	
	public String getTmpOutputFile() {
		return this.tmpOutputFile;
	}
	
	public boolean isTestInList(String test) {
		return this.junitTestList.contains(test);
	}
	
	public abstract TestExecResults run();
}
