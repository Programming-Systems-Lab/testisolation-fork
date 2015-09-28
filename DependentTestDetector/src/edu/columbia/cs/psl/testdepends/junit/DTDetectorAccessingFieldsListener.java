package edu.columbia.cs.psl.testdepends.junit;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import edu.washington.cs.dt.premain.Agent;
import edu.washington.cs.dt.premain.Tracer;
import edu.washington.cs.dt.util.Files;

public class DTDetectorAccessingFieldsListener extends RunListener{

	public void testStarted(org.junit.runner.Description description) throws Exception {
		Tracer.accessedFields = new HashSet<>();
	}
	private String getMethodName(Description desc) {
		if (desc == null)
			return "null";
		if (desc.getMethodName() == null)
			return desc.getDisplayName();
		else
			return desc.getMethodName();
	}

	private String getClassName(Description desc) {
		if (desc == null)
			return "null";
		if (desc.getClassName() == null)
			return desc.getTestClass().getName();
		else
			return desc.getClassName();
	}
	@Override
	public void testFailure(Failure failure) throws Exception {
//		System.out.println("BASEDIR:" + System.getProperty("basedir"));

		String writeFileFullPath = System.getProperty("DT_OUTPUT") + "/" + System.getProperty("basedir") + "/"+getClassName(failure.getDescription()) + "." + getMethodName(failure.getDescription()).replace("/", "--") + ".txt";
		System.out.println("Write to " + writeFileFullPath);
		Files.createIfNotExistNoExp(writeFileFullPath);
		Collection<String> fields = Tracer.getAccessedFields();
		Tracer.accessedFields = new HashSet<String>();
		Files.writeToFileWithNoExp(fields, writeFileFullPath);
	}
	public void testFinished(org.junit.runner.Description description) throws Exception {
//		System.out.println("BASEDIR:" + System.getProperty("basedir"));

		String writeFileFullPath = System.getProperty("DT_OUTPUT") + "/" + System.getProperty("basedir") + "/"+getClassName(description) + "." + getMethodName(description).replace("/", "--") + ".txt";
		System.out.println("Write to " + writeFileFullPath);
		Files.createIfNotExistNoExp(writeFileFullPath);
		Collection<String> fields = Tracer.getAccessedFields();
		Tracer.accessedFields = new HashSet<String>();
		Files.writeToFileWithNoExp(fields, writeFileFullPath);
	}
	static{
		if(Agent.shutdownHook != null)
		Runtime.getRuntime().removeShutdownHook(Agent.shutdownHook);
	}
}
