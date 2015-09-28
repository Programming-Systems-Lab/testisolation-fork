package edu.washington.cs.dt.seperatejvm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Scanner;

import junit.textui.ResultPrinter;

import org.junit.internal.TextListener;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.RunListener;

import plume.Option;
import plume.Options;
import edu.columbia.cs.psl.polldet.FindPackages;
import edu.columbia.cs.psl.testdepends.junit.JUnitDependencyListener;
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

	@Option("Switch to use the CU/EC approach for listening for dependencies")
	public static boolean useElectricDepends = false;
	
	@Option("Switch to capture selected polution based on prev results")
	public static boolean useSingleTestRecorder = false;
	
	@Option("Switch to use the UIUC approach for finding state pollution")
	public static boolean usePollDet = false;
	
	@Option("Switch to print times (in ms) per test method")
	public static String timingLog= null;
	
	public static boolean perMethodDependencies = true;
	
	static OurSecurityManager security = new OurSecurityManager();
	static {
		System.setSecurityManager(security);
	}
	static PrintWriter timingPrinter;
	public static void main(String[] args) throws FileNotFoundException {
		parseArgs(args);

		InstrumentSwitch.instrument = instrument;
				
		
		Scanner s = new Scanner(System.in);
		JUnitCore core = new JUnitCore();
		ExecutionEnv env = new ExecutionEnv(core);
		MyTestListener listener = new MyTestListener();
		core.addListener(listener);
//		core.addListener(new TextListener(System.out));
		RunListener dependListener = null;
		if (useElectricDepends) {
			try {
				Class<? extends RunListener> cl= (Class<? extends RunListener>) Class.forName("edu.columbia.cs.psl.testdepends.junit.DependencyReporter");
				 dependListener = cl.newInstance();
				core.addListener(new JUnitDependencyListener(dependListener));

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(useSingleTestRecorder)
		{
			try {
				Class<? extends RunListener> cl= (Class<? extends RunListener>) Class.forName("edu.columbia.cs.psl.testdepends.junit.SingleTestFieldReporter");
				 dependListener = cl.newInstance();
				core.addListener(new JUnitDependencyListener(dependListener));

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(usePollDet)
		{
			boolean includeJars = Boolean.valueOf(System.getProperty("includeDeps","false"));
			try {
				HashSet<String> paths = FindPackages.findPackages(System.getProperty("java.class.path"), includeJars);
				File f = new File("tmp_classwhitelist");
				System.setProperty("whitelist", f.getAbsolutePath());
				PrintWriter pw = new PrintWriter(f);
				for(String p : paths)
				{
					pw.println(p);
				}
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		if (usePollDet) {
//			try {
//				Class<? extends RunListener> cl= (Class<? extends RunListener>) Class.forName("edu.columbia.cs.psl.polldet.StatePollutionRunListener");
//				 dependListener = cl.newInstance();
//				core.addListener(new JUnitDependencyListener(dependListener));
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		if(timingLog != null)
		{
			timingPrinter = new PrintWriter(timingLog);
			RunListener timeLogger = new RunListener(){
				long started;
				@Override
				public void testStarted(Description description) throws Exception {
					started = System.currentTimeMillis();
					super.testStarted(description);
				}
				@Override
				public void testFinished(Description description) throws Exception {
					long time = System.currentTimeMillis() - started;
					timingPrinter.println(description.toString() +"\t"+time);
					super.testFinished(description);
				}
			};
			core.addListener(timeLogger);
		}
		JunitWrapper junit = new JunitWrapper(core, new TestRepFactory(), env, listener);
		TestBundle junit4Bundle = new TestBundle();
		try {
			while (s.hasNextLine()) {
				String line = s.nextLine();
				if(usePollDet)
				{
					System.out.println("WithPolLDet");
					System.setProperty("logFile","polldet_"+line);
					System.setProperty("projName", line);
					System.setProperty("verbose", "1");
				}
				if (line.contains(" ")) {
					//Run all methods at once.
					String oneMethod = line.substring(0, line.indexOf(' '));
					if (isJunit3Suite(oneMethod)) {
						junit4Bundle = junit4Bundle.runAndNew(junit);
						junit.run3InSuite(line);
					} else if (isJunit3NoSuite(oneMethod)) {
						junit4Bundle = junit4Bundle.runAndNew(junit);
						junit.runMethodRequest(line);
					} else if (isJunit4(oneMethod)) {
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
				} else {
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
			}
		} catch (StringIndexOutOfBoundsException ex) {
			s.close();
		}
		junit4Bundle.runAndNew(junit);
		env.popAll();
		s.close();
		if(dependListener != null)
			try {
				System.out.println("Finishing last test run");
				dependListener.testRunFinished(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(timingPrinter != null)
		{
			timingPrinter.flush();
			timingPrinter.close();
		}
		System.exit(0);
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
