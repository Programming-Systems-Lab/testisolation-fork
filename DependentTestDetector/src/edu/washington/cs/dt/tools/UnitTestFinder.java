package edu.washington.cs.dt.tools;

import java.io.IOException;
import java.util.List;

import edu.washington.cs.dt.util.Files;
import edu.washington.cs.dt.util.Globals;

public class UnitTestFinder {
	public static String outputFileName = "./allunittests.txt";
	public static String pathOrJarFile; //it can be a path or a jar
	
	public List<String> findAllTests() {
		return null;
	}
	
	public void saveToFile(List<String> allTests) {
		StringBuilder sb = new StringBuilder();
		for(String t : allTests) {
			sb.append(t);
			sb.append(Globals.lineSep);
		}
		try {
			Files.writeToFile(sb.toString(), outputFileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		UnitTestFinder finder = new UnitTestFinder();
		List<String> allTests = finder.findAllTests();
		finder.saveToFile(allTests);
	}
}