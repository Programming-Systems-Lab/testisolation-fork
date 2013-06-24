package edu.washington.cs.dt.abstractions;

import java.util.LinkedList;

import junit.framework.TestCase;

import org.junit.runner.JUnitCore;

import edu.washington.cs.dt.instrumentation.OurSecurityManager;

public class ExecutionEnv {
	private JUnitCore junit;

	public ExecutionEnv() {
		this.junit = new JUnitCore();
	}
	
	public ExecutionEnv(JUnitCore junit) {
		this.junit = junit;
	}
	
	LinkedList<InitializationRep> env = new LinkedList<InitializationRep>();
	
	public void runTest(TestRep test) {
		
		int stackPos = 0;
		for (int i = 0 ; i < test.env.size(); ++i) {
			InitializationRep setup = test.env.get(i);
			if (stackPos < env.size() && env.get(stackPos) == setup) {
				++stackPos;
				continue;
			} 
			while (stackPos < env.size()) { // remove rest of stack
				InitializationRep pop =  env.remove();
				pop.tearDown();
			}
			setup.setUp();
			env.add(setup);
			++stackPos;
		}
		
		TestCase  junitTest = (TestCase) test.test;
		junit.run(junitTest);
	}

	
	public void popAll() {
		while (env.size() > 0) { // remove rest of stack
			InitializationRep pop =  env.remove();
			pop.tearDown();
		}
	}
	

	
}
