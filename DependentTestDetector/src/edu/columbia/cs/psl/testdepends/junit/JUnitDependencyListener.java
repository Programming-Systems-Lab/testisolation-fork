package edu.columbia.cs.psl.testdepends.junit;

import java.io.Serializable;
import java.util.LinkedList;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class JUnitDependencyListener extends RunListener {
	RunListener delegate;
	public JUnitDependencyListener(RunListener delegate)
	{
		this.delegate = delegate;
	}
	public void testAssumptionFailure(Failure failure) {
		delegate.testAssumptionFailure(failure);
	}

	public void testFailure(Failure failure) throws Exception {
		delegate.testFailure(failure);
	}

	public void testFinished(Description description) throws Exception {
		delegate.testFinished(description);
	}

	public void testIgnored(Description description) throws Exception {
		delegate.testIgnored(description);
	}

	public void testRunFinished(Result result) throws Exception {
//		delegate.testRunFinished(result);
	}

	public void testRunStarted(Description description) throws Exception {
//		delegate.testRunStarted(description);
	}

	public void testStarted(Description description) throws Exception {
		delegate.testStarted(description);
	}
	
}
