package edu.washington.cs.dt.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipException;

import edu.washington.cs.dt.tools.UnitTestFinder;
import edu.washington.cs.dt.util.Utils;

import junit.framework.TestCase;

public class TestMain extends TestCase {

	public void testArgs1() {
		Main.main(new String[]{"--help"});
	}
	
	public void testArgs2() {
		Main.main(new String[]{"--minimize=true"});
	}
	
	public void testArgs3() {
		Main.main(new String[]{"--minimize=true", "--isolate"});
	}
	
	public void testArgs4() {
		Main.main(new String[]{"--minimize=true", "--isolate", "--reverse"});
	}
	
	public void testArgs5() {
		try {
		    Main.main(new String[]{"--minimize=true", "--isolate", "--tests=nontext"});
		    fail();
		} catch (RuntimeException e) {
			Utils.checkTrue(e.getCause() instanceof FileNotFoundException, "wrong exception types");
		}
	}
	
	public void testArgs6() {
		Main.main(new String[]{"--minimize=true", "--combination"});
	}
	
	public void testSimpleExamples1() throws ZipException, ClassNotFoundException, IOException {
		Main.main(new String[]{"--minimize=true", "--isolate", "--tests=./exampletests.txt"});
	}
	
	public void testSimpleExamples2() throws ZipException, ClassNotFoundException, IOException {
		Main.main(new String[]{"--minimize=true", "--reverse", "--tests=./exampletests.txt"});
	}
	
	public void testSimpleExamples3() throws ZipException, ClassNotFoundException, IOException {
		Main.main(new String[]{"--minimize=true", "--combination", "--k=2", "--tests=./exampletests.txt"});
	}
}
