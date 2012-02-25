package edu.washington.cs.dt.samples.junit4x;

import org.junit.Test;

public class ExampleJunit4xTest {

	static int x = 1;
	
	@Test
	public void testX() {
		x++;
		throw new RuntimeException();
	}
	
	public static void main(String[] args) {
//		junit.textui.TestRunner.main(new String[]{"-c",
//				"edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest"});
		
		org.junit.runner.JUnitCore.main("edu.washington.cs.dt.samples.junit4x.ExampleJunit4xTest");
	}
}
