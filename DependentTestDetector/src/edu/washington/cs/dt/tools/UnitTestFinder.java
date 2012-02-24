package edu.washington.cs.dt.tools;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipException;

import edu.washington.cs.dt.util.CodeUtils;
import edu.washington.cs.dt.util.Files;
import edu.washington.cs.dt.util.Globals;
import edu.washington.cs.dt.util.JarViewer;
import edu.washington.cs.dt.util.Utils;

public class UnitTestFinder {
	//two options
	public static String outputFileName = "./allunittests.txt";
	public static String pathOrJarFile; //it can be a path or a jar
	
	public List<String> findAllTests() throws ClassNotFoundException, ZipException, IOException {
		File f = new File(pathOrJarFile);
		if(f.isFile()) {
			Utils.checkTrue(f.getName().endsWith(".jar"), "Only support jar files, " +
					"but was given: " + f.getName());
			return getAllTestsFromJar(f);
		} else {
			return getAllTestsFromDir(f);
		}
	}
	
	private List<String> getAllTestsFromJar(File jarFile) throws ZipException, IOException, ClassNotFoundException {
		Collection<String> contents = JarViewer.getContentsAsStr(jarFile);
		List<String> tests = new LinkedList<String>();
		for(String content : contents) {
			if(content.endsWith(".class")) {
				String clzName = content.replace("/", ".").substring(0, content.indexOf(".class"));
//				System.out.println(clzName);
				//skip checking whether it is JUnit class or not
				Class<?> clz = Class.forName(clzName);
				if(!CodeUtils.isInstantiableJUnitClass(clz)) {
			    	continue;
			    }
			    Method[] methods = clz.getMethods();
				for(Method method : methods) {
					if(CodeUtils.isJUnitMethod(method)) {
//					    System.out.println("   method: " + method.getName());
					    String testName = clz.getName() + "." + method.getName();
					    tests.add(testName);
					}
				}
			}
		}
		return tests;
	}
	
	private List<String> getAllTestsFromDir(File dir) throws ClassNotFoundException {
		Collection<File> files = Files.listFiles(dir, null, true);
		List<String> tests = new LinkedList<String>();
		for(File f : files) {
			if(f.getName().endsWith(".class")) {
				String clzName = CodeUtils.pathToClass(f, dir.getAbsolutePath());
//			    System.out.println(clzName);
			    Class<?> clz = Class.forName(clzName);
			    //skip checking whether it is JUnit class or not
			    if(!CodeUtils.isInstantiableJUnitClass(clz)) {
			    	continue;
			    }
			    Method[] methods = clz.getMethods();
				for(Method method : methods) {
					if(CodeUtils.isJUnitMethod(method)) {
//					    System.out.println("   method: " + method.getName());
					    String testName = clz.getName() + "." + method.getName();
					    tests.add(testName);
					}
				}
			}
		}
		return tests;
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
	
	public static void main(String[] args) throws ClassNotFoundException, ZipException, IOException {
		UnitTestFinder finder = new UnitTestFinder();
		List<String> allTests = finder.findAllTests();
		finder.saveToFile(allTests);
	}
}