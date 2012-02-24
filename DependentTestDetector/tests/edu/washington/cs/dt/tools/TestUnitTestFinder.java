package edu.washington.cs.dt.tools;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipException;

import junit.framework.TestCase;

public class TestUnitTestFinder extends TestCase {
	
	public void testFindClassInJar() throws ZipException, ClassNotFoundException, IOException {
		//this jar must be in classpath
		UnitTestFinder.pathOrJarFile = "D:\\research\\testisolation\\Workspace\\DependentTestDetector\\jtopas.jar";
		UnitTestFinder finder = new UnitTestFinder();
		List<String> allTests = finder.findAllTests();
		finder.saveToFile(allTests);
	}
	
	public void testFindClassInPath() throws ZipException, ClassNotFoundException, IOException {
		//this dir must be in classpath
		UnitTestFinder.pathOrJarFile = "D:\\research\\testisolation\\Workspace\\DependentTestDetector\\bin";
		UnitTestFinder finder = new UnitTestFinder();
		List<String> allTests = finder.findAllTests();
		finder.saveToFile(allTests);
	}
	
	public void tearDown() {
		UnitTestFinder.pathOrJarFile = null;
	}
	
}
