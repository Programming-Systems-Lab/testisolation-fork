package edu.washington.cs.dt.samples.junit4x;

import org.junit.Ignore;
import org.junit.Test;

public class ExampleForUnitTestFinder {
	@Test
	public void testX() {
	}
	
	@Test
	@Ignore("Ignore this")
	public void testY() {
	}
	
	public void testZ() {
		
	}
}
