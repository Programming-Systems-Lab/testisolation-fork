package edu.washington.cs.dt.util;

import java.util.regex.Pattern;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import junit.framework.TestResult;
import junit.textui.TestRunner;
import edu.washington.cs.dt.RESULT;
import edu.washington.cs.dt.main.Main;


/**
 * This class is in too low level. I made it package-visible
 * on purpose. 
 * */
class JUnitTestExecutor {

	private String result = null;
	private String stackTrace = TestExecUtils.noStackTrace;
	
	public final Class<?> junitTest;
	public final String junitMethod;
	public final String fullMethodName;
	
	//package.class.method
	public JUnitTestExecutor(String fullMethodName) {
		this.fullMethodName = fullMethodName;
		String className = this.fullMethodName.substring(0, this.fullMethodName.lastIndexOf("."));
		try {
			Class<?> clzName = Class.forName(className);
			this.junitTest = clzName;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		this.junitMethod = this.fullMethodName.substring(this.fullMethodName.lastIndexOf(".") + 1);
	}
	
	public JUnitTestExecutor(String className, String junitMethod) {
		try {
			Class<?> clzName = Class.forName(className);
			this.junitTest = clzName;
			this.junitMethod = junitMethod;
			this.fullMethodName = this.junitTest.getName() + "." + junitMethod;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	public JUnitTestExecutor(Class<?> junitTest, String junitMethod) {
		this.junitTest = junitTest;
		this.junitMethod = junitMethod;
		this.fullMethodName = this.junitTest.getName() + "." + junitMethod;
	}
	
	public void executeJUnit4() {
        JUnitCore core = new JUnitCore();
		Request r = Request.method(this.junitTest, this.junitMethod);
		Result re = core.run(r);
		Utils.checkTrue(re.getRunCount() == 1, "Running: " + this.junitMethod);
		
		if(re.getFailureCount() == 0) {
			result = RESULT.PASS.name();
		} else {
			Utils.checkTrue(re.getFailureCount() == 1,
					"Running: " + this.fullMethodName + ", failure count: " + re.getFailureCount());
			//check whether it is a failure or an error
            Failure f = re.getFailures().get(0);
            Throwable excep = f.getException();
            if(isJUnitAssertionFailure(excep)) {
            	result = RESULT.FAILURE.name();
            } else {
            	result = RESULT.ERROR.name();
            }
            //get the stack trace
            stackTrace = flatStackTrace(excep);
		}
	}
	
	public static boolean isJUnitAssertionFailure(Throwable exception) {
		return exception.getClass().equals(junit.framework.AssertionFailedError.class)
		      || exception.getClass().equals(junit.framework.ComparisonFailure.class);
	}
	
	public static String flatStackTrace(Throwable exception) {
		String[] stacktraces = TestExecUtils.extractStackTraces(exception);
		Pattern p = Pattern.compile(Main.excludeRegex);
		String flatString = TestExecUtils.flatStrings(stacktraces, p, TestExecUtils.JUNIT_ASSERT);
		return flatString;
	}
	
	public void executeJUnit3() {
		try {
			TestRunner aTestRunner= new TestRunner();
			String[] junitArgs = new String[]{"-m", this.fullMethodName};
			TestResult r = aTestRunner.start(junitArgs);
			if(r.wasSuccessful()) {
				result = RESULT.PASS.name();
			} else {
				if(r.errorCount() > 0) {
					Utils.checkTrue(r.errorCount() == 1, "Only execute 1 test: " + this.fullMethodName
							+ ", two errors: " + CodeUtils.flattenFailrues(r.errors()));
					result = RESULT.ERROR.name();
					stackTrace = TestExecUtils.flatStackTrace(r.errors().nextElement(), Main.excludeRegex);
				}
				if(r.failureCount() > 0) {
					Utils.checkTrue(r.failureCount() == 1, "Only execute 1 test: " + this.fullMethodName
							+ ", two failures: " + CodeUtils.flattenFailrues(r.failures()));
					result = RESULT.FAILURE.name();
					stackTrace = TestExecUtils.flatStackTrace(r.failures().nextElement(), Main.excludeRegex);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getResult() {
		return this.result;
	}
	
	public String getStackTrace() {
		return this.stackTrace;
	}
}
