package edu.washington.cs.dt.util;

import junit.framework.TestCase;

public class TestPermutationGenerator extends TestCase {

	public void testPermutation0() {
		PermutationGenerator g = new PermutationGenerator(3, 2);
		assertEquals(6, g.getPermutationNum());
		
		while(g.hasNext()) {
			int[] next = g.getNext();
			System.out.println(Utils.convertArrayToFlatString(next));
		}
	}
	
}