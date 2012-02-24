package edu.washington.cs.dt;

import java.util.LinkedList;
import java.util.List;

import edu.washington.cs.dt.util.Globals;

import junit.framework.TestCase;

public class TestDependentTestDetector extends TestCase {
	
	List<String> tests = new LinkedList<String>();
	
	public void setUp() {
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test1");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test2");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test3");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test4");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test5");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test6");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testDummy");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testDummy1");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testDummy2");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr1");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr2");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr3");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr4");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.testStr5");
	}

	public void testIsolation() {
		DependentTestIdentifier identifier = new DependentTestIdentifier(tests);
		identifier.setMinimize(true);
		List<TestExecResultsDelta> l = identifier.findDependenceForIsolation();
		System.out.println(l);
	}
	
	public void testReverse() {
		DependentTestIdentifier identifier = new DependentTestIdentifier(tests);
		identifier.setMinimize(true);
		List<TestExecResultsDelta> l = identifier.findDependenceForReverse();
		System.out.println(l);
	}
	
	public void testCombine() {
		tests.clear();
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test1");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test2");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test3");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test4");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test5");
		tests.add("edu.washington.cs.dt.samples.TestShareGlobals.test6");
		
		DependentTestIdentifier identifier = new DependentTestIdentifier(tests);
		identifier.setMinimize(true);
		List<TestExecResultsDelta> l = identifier.findDependenceForCombination(4);
		for(TestExecResultsDelta d : l) {
		    System.out.println(d);
		    System.out.println(Globals.lineSep);
		}
	}
}
