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
	
	public void testArgs1() throws ZipException, ClassNotFoundException, IOException {
		UnitTestFinder.main(new String[]{"--help"});
	}
	
	public void testArgs2() throws ZipException, ClassNotFoundException, IOException {
		UnitTestFinder.main(new String[]{"--outputFileName=./text"});
	}
	
	public void testArgs3() throws ZipException, ClassNotFoundException, IOException {
		UnitTestFinder.main(new String[]{"--pathOrJarFile=./a.jar"});
	}
	
	public void tearDown() {
		UnitTestFinder.pathOrJarFile = null;
	}
	
}
