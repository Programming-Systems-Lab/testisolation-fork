package edu.washington.cs.dt.abstractions;

import java.util.LinkedList;

import junit.framework.Test;

public class TestRep {

	public Test test;
	public LinkedList<InitializationRep> env;
	private int number;
	private String suiteName;

	public TestRep(Test test, LinkedList<InitializationRep> setups, String suiteName, int number) {
		this.test = test;
		this.env = new LinkedList<InitializationRep>();
		this.suiteName = suiteName;
		this.number = number;
		copy(setups);
	}

	
	private void copy(LinkedList<InitializationRep> env) {
		for (InitializationRep setup:env) {
			this.env.add(setup);
		}
	}


	@Override
	public String toString() {
		return "<"+suiteName+  ":" + number + ">";
	}
}
