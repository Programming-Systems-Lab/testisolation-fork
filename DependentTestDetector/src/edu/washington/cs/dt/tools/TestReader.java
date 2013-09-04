package edu.washington.cs.dt.tools;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.washington.cs.dt.util.Files;
import edu.washington.cs.dt.util.Utils;

public class TestReader {
	
	public static String xml_manual_pairwise = "E:\\testisolation\\dependence-aware-folder\\xml_security_manual_pairwise\\";

	public static Set<List<String>>  readTestsFromAllFiles(String folderName, int numPerFile) {
		Set<List<String>> tests = new LinkedHashSet<List<String>>();
		
		Collection<File> files = Files.listFiles(new File(folderName), null, false);
//		System.out.println(files.size());
		for(File f : files) {
			List<String> testsInFile = Files.readWholeNoExp(f.getAbsolutePath());
			Utils.checkTrue(testsInFile.size() == numPerFile, "Not equal");
			tests.add(testsInFile);
		}
		
		return tests;
	}
	
	public static void main(String[] args) {
		Set<List<String>> allTests = readTestsFromAllFiles(xml_manual_pairwise, 2);
		System.out.println("test num: " + allTests.size());
	}
	
}
