package edu.washington.cs.dt.seperatejvm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

import edu.washington.cs.dt.abstractions.ExecutionEnv;
import edu.washington.cs.dt.abstractions.TestRep;
import edu.washington.cs.dt.instrumentation.InstrumentSwitch;
import edu.washington.cs.dt.util.CodeUtils;

public class JunitWrapper {
	
	JUnitCore junit ;
	private ExecutionEnv env;
	private TestRepFactory factory;
	private MyTestListener listener;
	
	public JunitWrapper(JUnitCore junit, TestRepFactory factory, ExecutionEnv env, MyTestListener listener) {
		this.junit = junit;
		this.factory = factory;
		this.env = env;
		this.listener = listener;
	}
	
	public void run3InSuite(String line) {
		Pattern p = Pattern.compile("<(.*):(\\d+)>");
		Matcher m = p.matcher(line);
		m.matches();
		String methodName = m.group(1);
		int number = Integer.parseInt(m.group(2));
		TestRep rep = factory.getTest(methodName, number);
		listener.addTest(line);
		
		if (TestRunner.instrumentSetups) {
			InstrumentSwitch.start();
		}
		
		env.runTest(rep);
		
		if (TestRunner.tearDownAll) {
			env.popAll();
		}
		
		if (TestRunner.instrumentSetups) {
			InstrumentSwitch.stop(listener.getLastResult());
		}
	}

	public void runMethodRequest(String line) {
		Request req = Request.method(CodeUtils.forName(CodeUtils.getClassNameFromMethodName(line)), 
				CodeUtils.getMethodNameFromMethodName(line));
		listener.addTest(line);
		if (TestRunner.instrumentSetups) {
			InstrumentSwitch.start();
		}
		
		junit.run(req);
		if (TestRunner.instrumentSetups) {
			InstrumentSwitch.stop(listener.getLastResult());
		}
		
	}

	public void runRuner(MyRunner myRunner) {
		listener.addTests(myRunner.getTestNames());
		if (TestRunner.instrumentSetups) {
			InstrumentSwitch.start();
		}
		junit.run(myRunner);
		if (TestRunner.instrumentSetups) {
			InstrumentSwitch.stop(listener.getLastResult());
		} 
	}	
}
