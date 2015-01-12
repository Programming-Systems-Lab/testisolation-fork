package edu.washington.cs.dt.seperatejvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import edu.washington.cs.dt.RESULT;
import edu.washington.cs.dt.instrumentation.InstrumentSwitch;
import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.Globals;
import edu.washington.cs.dt.util.TestExecUtils;

public class MyTestListener extends RunListener {

	private List<String> testNames;
	int counter = 0;
	private long startTime;
	private String result = RESULT.PASS.name();
	private String stackTrace = TestExecUtils.noStackTrace;
	private String fullStackTrace = TestExecUtils.noStackTrace;
	

	public MyTestListener() {
		this.testNames = new ArrayList<>();
	}
	
	public void addTest(String name) {
		this.testNames.add(name);
	}
	
	public void addTests(Collection<String> names) {
		this.testNames.addAll(names);
	}
	
	public MyTestListener(String singleTestName) {
		this.testNames = new ArrayList<String>();
		this.testNames.add(singleTestName);
	}
	
	
	@Override
	public void testStarted(Description description) throws Exception {
		this.startTime = System.currentTimeMillis();
		
		if (TestRunner.instrumentSetups) {
			if (!isFirst()) {
				InstrumentSwitch.start();
			}
		} else {
			InstrumentSwitch.start();
		}
		result = RESULT.PASS.name();
		fullStackTrace = TestExecUtils.noStackTrace;	
	}
	
	private boolean isFirst() {
		return this.counter == 0;
	}

	String lastResult = "";
	
	@Override
	public void testFinished(Description description) throws Exception {
		
		if (! description.isTest()) {
			return;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(Globals.stdoutPrefix);
		sb.append(testNames.get(counter));
		sb.append(TestExecUtils.testResultSep);
		sb.append(result);
		sb.append(TestExecUtils.testResultSep);
		sb.append(System.currentTimeMillis() - this.startTime);			
		sb.append(TestExecUtils.resultExcepSep);
		//sb.append(stackTrace);
		sb.append(fullStackTrace);
		sb.append(Globals.lineSep);
		

		this.lastResult = sb.toString();
		
		if (TestRunner.instrumentSetups) {
			if (!isLast()) {
				InstrumentSwitch.stop(this.lastResult);
			}
		} else {
			InstrumentSwitch.stop(this.lastResult);
		}
		
		
		
		++this.counter;
	}
	
	public String getLastResult() {
		return lastResult;
	}
	
	private boolean isLast() {
		return this.counter == this.testNames.size() - 1;
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
	      Throwable excep = failure.getException();
          if(isJUnitAssertionFailure(excep)) {
          	result = RESULT.FAILURE.name();
          } else {
          	result = RESULT.ERROR.name();
          } 
          stackTrace = flatStackTrace(excep);           //get the stack trace
          fullStackTrace = TestExecUtils.flatStrings(TestExecUtils.extractStackTraces(excep));
	}
	
	public static String flatStackTrace(Throwable exception) {
		String[] stacktraces = TestExecUtils.extractStackTraces(exception);
		Pattern p = Pattern.compile(Main.excludeRegex);
		String flatString = TestExecUtils.flatStrings(stacktraces, p, TestExecUtils.JUNIT_ASSERT);
		return flatString;
	}

	public static boolean isJUnitAssertionFailure(Throwable exception) {
		return exception.getClass().equals(junit.framework.AssertionFailedError.class)
		      || exception.getClass().equals(junit.framework.ComparisonFailure.class);
	}
	
}