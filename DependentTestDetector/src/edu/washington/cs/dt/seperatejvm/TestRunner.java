package edu.washington.cs.dt.seperatejvm;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Scanner;

import org.junit.runner.JUnitCore;

import plume.Option;
import plume.Options;
import edu.washington.cs.dt.abstractions.ExecutionEnv;
import edu.washington.cs.dt.instrumentation.InstrumentSwitch;
import edu.washington.cs.dt.instrumentation.OurSecurityManager;
import edu.washington.cs.dt.util.CodeUtils;


public class TestRunner {
	@Option("Tear down all setup after a test is called. Not default junit behavior.")
	public static boolean tearDownAll = true;
	
	@Option("Instrumentation will be on for setups and tear down. Only makes sense for tearDownAll enabled.")
	public static boolean instrumentSetups = true;
	
	@Option("Turn instrumentation off / on.")
	public static boolean instrument = false;

	
	static OurSecurityManager security = new OurSecurityManager();
	static {
		System.setSecurityManager(security);
	}

	public static void main(String[] args) throws FileNotFoundException {
		parseArgs(args);

		InstrumentSwitch.instrument = instrument;
				
		
		Scanner s = new Scanner(System.in);
		JUnitCore core = new JUnitCore();
		ExecutionEnv env = new ExecutionEnv(core);

		MyTestListener listener = new MyTestListener();
		core.addListener(listener);
		
		JunitWrapper junit = new JunitWrapper(core, new TestRepFactory(), env, listener);
		TestBundle junit4Bundle = new TestBundle();

		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (isJunit3Suite(line)) {
				junit4Bundle = junit4Bundle.runAndNew(junit);
				junit.run3InSuite(line);
			} else if (isJunit3NoSuite(line)) {
				junit4Bundle = junit4Bundle.runAndNew(junit);
				junit.runMethodRequest(line);
			} else if (isJunit4(line)) {
				if (tearDownAll) {
					junit.runMethodRequest(line);
				} else {
					if (junit4Bundle.canAdd(line)) {
						junit4Bundle.add(line);
					} else {
						junit4Bundle = junit4Bundle.runAndNew(junit);
						junit4Bundle.add(line);
					}
				}
			} else {
				s.close();
				throw new RuntimeException("Unexpected line");
			}
		}
		
		junit4Bundle.runAndNew(junit);
		env.popAll();
		s.close();
	}

	private static void parseArgs(String[] args) {
		Options options = new Options("Test runner", TestRunner.class);
		options.parse_or_usage(args);
	}

	private static boolean isJunit4(String line) {
		String className = CodeUtils.getClassNameFromMethodName(line);
		Class<?> klass = CodeUtils.forName(className);
		Method m = CodeUtils.getMethod(klass, CodeUtils.getMethodNameFromMethodName(line));
		return CodeUtils.isJUnit4XMethod(m);
	}

	private static boolean isJunit3NoSuite(String line) {
		String className = CodeUtils.getClassNameFromMethodName(line);
		Class<?> klass = CodeUtils.forName(className);
		return CodeUtils.isJUnit3Class(klass);
	}

	private static boolean isJunit3Suite(String line) {
		return line.startsWith("<");
	}
}
