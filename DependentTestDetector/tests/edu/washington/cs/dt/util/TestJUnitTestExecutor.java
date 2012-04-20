package edu.washington.cs.dt.util;

import edu.washington.cs.dt.RESULT;
import edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestJUnitTestExecutor extends TestCase {
	
	public static Test suite() {
		return new TestSuite(TestJUnitTestExecutor.class);
	}
	
	public void testPassJUnit4() {
		JUnitTestExecutor executor = new JUnitTestExecutor(ExampleJunit4xTest.class, "testX");
		executor.executeJUnit4();
		assertEquals(RESULT.PASS.name(), executor.getResult());
		assertEquals(TestExecUtils.noStackTrace, executor.getStackTrace());
		
		executor = new JUnitTestExecutor("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testX");
		executor.executeJUnit4();
		assertEquals(RESULT.PASS.name(), executor.getResult());
		assertTrue(TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
		
		executor = new JUnitTestExecutor("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest", "testX");
		executor.executeJUnit4();
		assertEquals(RESULT.PASS.name(), executor.getResult());
		assertTrue(TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
	}
	
	public void testFail1JUnit4() {
		JUnitTestExecutor executor = new JUnitTestExecutor(ExampleJunit4xTest.class, "testE");
		executor.executeJUnit4();
		assertEquals(RESULT.FAILURE.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
		
		executor = new JUnitTestExecutor("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testE");
		executor.executeJUnit4();
		assertEquals(RESULT.FAILURE.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
		
		executor = new JUnitTestExecutor("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest", "testE");
		executor.executeJUnit4();
		assertEquals(RESULT.FAILURE.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
	}
	
	public void testFail2JUnit4() {
		JUnitTestExecutor executor = new JUnitTestExecutor(ExampleJunit4xTest.class, "testF");
		executor.executeJUnit4();
		assertEquals(RESULT.FAILURE.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
		
		executor = new JUnitTestExecutor("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testF");
		executor.executeJUnit4();
		assertEquals(RESULT.FAILURE.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
		
		executor = new JUnitTestExecutor("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest", "testF");
		executor.executeJUnit4();
		assertEquals(RESULT.FAILURE.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
	}
	
	public void testErrorJUnit4() {
		JUnitTestExecutor executor = new JUnitTestExecutor(ExampleJunit4xTest.class, "testZ");
		executor.executeJUnit4();
		assertEquals(RESULT.ERROR.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
		
		executor = new JUnitTestExecutor("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest.testZ");
		executor.executeJUnit4();
		assertEquals(RESULT.ERROR.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
		
		executor = new JUnitTestExecutor("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest", "testZ");
		executor.executeJUnit4();
		assertEquals(RESULT.ERROR.name(), executor.getResult());
		assertTrue(!TestExecUtils.noStackTrace.equals(executor.getStackTrace()));
	}

}
