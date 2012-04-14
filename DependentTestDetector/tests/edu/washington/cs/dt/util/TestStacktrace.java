package edu.washington.cs.dt.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestStacktrace extends TestCase {
	static final String className = "edu.washington.cs.dt.samples.TestShareGlobals";
	
	public static Test suite() {
		return new TestSuite(TestStacktrace.class);
	}

	public void testExcludingJUnitStackTrace() throws IOException {
		String[] args = new String[]{
				"./tests/edu/washington/cs/dt/samples/havefails.txt",
				className + ".test1",
				className + ".test2",
				className + ".test4",
				className + ".test5"
		};
		TestRunnerWrapper.main(args);
	}
}
