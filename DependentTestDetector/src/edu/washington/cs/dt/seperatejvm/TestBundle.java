package edu.washington.cs.dt.seperatejvm;

import java.util.ArrayList;

import org.junit.runners.model.InitializationError;

import edu.washington.cs.dt.util.CodeUtils;

public class TestBundle {

	ArrayList<String> tests = new ArrayList<String>();
	public String className = null;
	
	public boolean canAdd(String methodName) {
		if (className == null) {
			return true;
		}
		return this.className.equals(CodeUtils.getClassNameFromMethodName(methodName));
	}
	
	public void add(String methodName) {
		this.className = CodeUtils.getClassNameFromMethodName(methodName);
		this.tests.add(CodeUtils.getMethodNameFromMethodName(methodName));
	}
	
	public TestBundle runAndNew(JunitWrapper junit) {
		if (tests.size() == 0) {
			return this;
		}
		
		try {
			junit.runRuner(new MyRunner(CodeUtils.forName(this.className), tests));
		} catch (InitializationError e) {
			throw new RuntimeException(e);
		}
		return new TestBundle();
	}
}
