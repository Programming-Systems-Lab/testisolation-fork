package edu.washington.cs.dt;

import java.util.LinkedList;
import java.util.List;

import edu.washington.cs.dt.runners.AbstractTestRunner;
import edu.washington.cs.dt.runners.CombinatorialRunner;
import edu.washington.cs.dt.runners.FixedOrderRunner;
import edu.washington.cs.dt.runners.IsolationRunner;

import junit.framework.TestCase;

public class TestExecutionResultsDifferentior extends TestCase {
	
	List<String> tests = new LinkedList<String>();
	
	public void setUp() {
		tests = new LinkedList<String>();
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
	
	public void testIsolationBehaviorDiffs() {
		AbstractTestRunner fixedOrderRunner = new FixedOrderRunner(tests);
		AbstractTestRunner isolationRunner = new IsolationRunner(tests);
		
		TestExecResult foR = fixedOrderRunner.run().getExecutionRecords().get(0);
		TestExecResults isoRs = isolationRunner.run();
		
		TestExecResultsDifferentior differ = new TestExecResultsDifferentior(foR, isoRs);
		List<TestExecResultsDelta> deltas = differ.diffResults();
		for(TestExecResultsDelta d : deltas) {
			System.out.println(d);
		}
	}

	public void testCombinatorialDiffs() {
		AbstractTestRunner fixedOrderRunner = new FixedOrderRunner(tests);
		AbstractTestRunner isolationRunner = new CombinatorialRunner(tests, 2);
		
		TestExecResult foR = fixedOrderRunner.run().getExecutionRecords().get(0);
		TestExecResults isoRs = isolationRunner.run();
		
		TestExecResultsDifferentior differ = new TestExecResultsDifferentior(foR, isoRs);
		List<TestExecResultsDelta> deltas = differ.diffResults();
		for(TestExecResultsDelta d : deltas) {
			System.out.println(d);
		}
	}
}
