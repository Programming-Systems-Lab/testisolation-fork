package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * <p>A <code>TestSuite</code> is a <code>Composite</code> of Tests.
 * It runs a collection of test cases. Here is an example using
 * the dynamic test definition.
 * <pre>
 * TestSuite suite= new TestSuite();
 * suite.addTest(new MathTest("testAdd"));
 * suite.addTest(new MathTest("testDivideByZero"));
 * </pre>
 * </p>
 * 
 * <p>Alternatively, a TestSuite can extract the tests to be run automatically.
 * To do so you pass the class of your TestCase class to the
 * TestSuite constructor.
 * <pre>
 * TestSuite suite= new TestSuite(MathTest.class);
 * </pre>
 * </p>
 * 
 * <p>This constructor creates a suite with all the methods
 * starting with "test" that take no arguments.</p>
 * 
 * <p>A final option is to do the same for a large array of test classes.
 * <pre>
 * Class[] testClasses = { MathTest.class, AnotherTest.class }
 * TestSuite suite= new TestSuite(testClasses);
 * </pre>
 * </p>
 *
 * @see Test
 */
public class TestSuite implements Test {

	/**
	 * ...as the moon sets over the early morning Merlin, Oregon
	 * mountains, our intrepid adventurers type...
	 */
	static public Test createTest(Class<?> theClass, String name) {
		Constructor<?> constructor;
		try {
			constructor= getTestConstructor(theClass);
		} catch (NoSuchMethodException e) {
			return warning("Class "+theClass.getName()+" has no public constructor TestCase(String name) or TestCase()");
		}
		Object test;
		try {
			if (constructor.getParameterTypes().length == 0) {
				test= constructor.newInstance(new Object[0]);
				if (test instanceof TestCase)
					((TestCase) test).setName(name);
			} else {
				test= constructor.newInstance(new Object[]{name});
			}
		} catch (InstantiationException e) {
			return(warning("Cannot instantiate test case: "+name+" ("+exceptionToString(e)+")"));
		} catch (InvocationTargetException e) {
			return(warning("Exception in constructor: "+name+" ("+exceptionToString(e.getTargetException())+")"));
		} catch (IllegalAccessException e) {
			return(warning("Cannot access test case: "+name+" ("+exceptionToString(e)+")"));
		}
		return (Test) test;
	}
	
	/**
	 * Gets a constructor which takes a single String as
	 * its argument or a no arg constructor.
	 */
	public static Constructor<?> getTestConstructor(Class<?> theClass) throws NoSuchMethodException {
		try {
			return theClass.getConstructor(String.class);	
		} catch (NoSuchMethodException e) {
			// fall through
		}
		return theClass.getConstructor(new Class[0]);
	}

	/**
	 * Returns a test which will fail and log a warning message.
	 */
	public static Test warning(final String message) {
		return new TestCase("warning") {
			@Override
			protected void runTest() {
				fail(message);
			}

			// WL do not think we need to actually change this
			public List<Test> getTests() {
				// TODO Auto-generated method stub
				return null;
			}

			public void run(TestResult fTestResult, List<String> testNames) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	/**
	 * Converts the stack trace into a string
	 */
	private static String exceptionToString(Throwable t) {
		StringWriter stringWriter= new StringWriter();
		PrintWriter writer= new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		return stringWriter.toString();
	}
	
	private String fName;

	private Vector<Test> fTests= new Vector<Test>(10); // Cannot convert this to List because it is used directly by some test runners

    /**
	 * Constructs an empty TestSuite.
	 */
	public TestSuite() {
	}
	
	/**
	 * Constructs a TestSuite from the given class. Adds all the methods
	 * starting with "test" as test cases to the suite.
	 * Parts of this method were written at 2337 meters in the Hueffihuette,
	 * Kanton Uri
	 */
	public TestSuite(final Class<?> theClass) {
		addTestsFromTestCase(theClass);
	}

	private void addTestsFromTestCase(final Class<?> theClass) {
		fName= theClass.getName();
		try {
			getTestConstructor(theClass); // Avoid generating multiple error messages
		} catch (NoSuchMethodException e) {
			addTest(warning("Class "+theClass.getName()+" has no public constructor TestCase(String name) or TestCase()"));
			return;
		}

		if (!Modifier.isPublic(theClass.getModifiers())) {
			addTest(warning("Class "+theClass.getName()+" is not public"));
			return;
		}

		Class<?> superClass= theClass;
		List<String> names= new ArrayList<String>();
		while (Test.class.isAssignableFrom(superClass)) {
			for (Method each : superClass.getDeclaredMethods())
				addTestMethod(each, names, theClass);
			superClass= superClass.getSuperclass();
		}
		if (fTests.size() == 0)
			addTest(warning("No tests found in "+theClass.getName()));
	}
	
	/**
	 * Constructs a TestSuite from the given class with the given name.
	 * @see TestSuite#TestSuite(Class)
	 */
	public TestSuite(Class<? extends TestCase>  theClass, String name) {
		this(theClass);
		setName(name);
	}
	
   	/**
	 * Constructs an empty TestSuite.
	 */
	public TestSuite(String name) {
		setName(name);
	}
	
	/**
	 * Constructs a TestSuite from the given array of classes.  
	 * @param classes {@link TestCase}s
	 */
	public TestSuite (Class<?>... classes) {
		for (Class<?> each : classes)
			addTest(testCaseForClass(each));
	}

	private Test testCaseForClass(Class<?> each) {
		if (TestCase.class.isAssignableFrom(each))
			return new TestSuite(each.asSubclass(TestCase.class));
		else
			return warning(each.getCanonicalName() + " does not extend TestCase");
	}
	
	/**
	 * Constructs a TestSuite from the given array of classes with the given name.
	 * @see TestSuite#TestSuite(Class[])
	 */
	public TestSuite(Class<? extends TestCase>[] classes, String name) {
		this(classes);
		setName(name);
	}
	
	/**
	 * Adds a test to the suite.
	 */
	public void addTest(Test test) {
		fTests.add(test);
	}

	/**
	 * Adds the tests from the given class to the suite
	 */
	public void addTestSuite(Class<? extends TestCase> testClass) {
		addTest(new TestSuite(testClass));
	}
	
	/**
	 * Counts the number of test cases that will be run by this test.
	 */
	public int countTestCases() {
		int count= 0;
		for (Test each : fTests)
			count+=  each.countTestCases();
		return count;
	}

	/**
	 * Returns the name of the suite. Not all
	 * test suites have a name and this method
	 * can return null.
	 */
	public String getName() {
		return fName;
	}
	 
	/**
	 * Runs the tests and collects their result in a TestResult.
	 */
	public void run(TestResult result) {
		for (Test each : fTests) {
	  		if (result.shouldStop() )
	  			break;
//	  		System.out.println(each);
			runTest(each, result);
		}
	}

	/**
	 * WL
	 * Runs the tests and collects their result in a TestResult.
	 */
	public void run(TestResult result, List<String> testList) {
		// build a map of test names to the test
		HashMap<String, Test> testMap = new HashMap<String, Test>();
		if (this.getClass() == TestSuite.class) 
			for (int i = 0; i < fTests.size(); i++) {
				if (fTests.get(i).getClass()== TestSuite.class) 
					buildTestMap((TestSuite) fTests.get(i), testMap);
			}
		// this is not a test suite
		else 
			for (int i = 0; i < fTests.size(); i++) 
				testMap.put(fTests.get(i).toString(), fTests.get(i));
		
		for (int i = 0; i < testList.size(); i++) {
			String currTestName = testList.get(i);
			if (testMap.containsKey(currTestName)) {
				Test currTest = testMap.get(testList.get(i));
				currTest.run(result);
			} else {
				System.err.println("Input file contained tests that does not exist in this plugin.");
				break;
			}
		}
	}

	private void buildTestMap(TestSuite curr, HashMap<String, Test> testMap) {
		for (int i = 0; i < curr.fTests.size(); i++) {
			// not sure if this is needed to recursively get tests
			if (extendsTestSuite(curr, i)) 
				buildTestMap((TestSuite) curr.fTests.get(i), testMap);
			else 
				testMap.put(curr.fTests.get(i).toString(), curr.fTests.get(i));
		}		
	}
	
	public void runTest(Test test, TestResult result) {
		test.run(result);
	}
	 
	/**
	 * Sets the name of the suite.
	 * @param name the name to set
	 */
	public void setName(String name) {
		fName= name;
	}

	/**
	 * Returns the test at the given index
	 */
	public Test testAt(int index) {
		return fTests.get(index);
	}
	
	/**
	 * Returns the number of tests in this suite
	 */
	public int testCount() {
		return fTests.size();
	}
	
	/**
	 * Returns the tests as an enumeration
	 */
	public Enumeration<Test> tests() {
		return fTests.elements();
	}
	
	/**
	 */
	@Override
	public String toString() {
		if (getName() != null)
			return getName();
		return super.toString();
	 }

	private void addTestMethod(Method m, List<String> names, Class<?> theClass) {
		String name= m.getName();
		if (names.contains(name))
			return;
		if (! isPublicTestMethod(m)) {
			if (isTestMethod(m))
				addTest(warning("Test method isn't public: "+ m.getName() + "(" + theClass.getCanonicalName() + ")"));
			return;
		}
		names.add(name);
		addTest(createTest(theClass, name));
	}

	private boolean isPublicTestMethod(Method m) {
		return isTestMethod(m) && Modifier.isPublic(m.getModifiers());
	 }
	 
	private boolean isTestMethod(Method m) {
		return 
			m.getParameterTypes().length == 0 && 
			m.getName().startsWith("test") && 
			m.getReturnType().equals(Void.TYPE);
	 }

	/**
	 * WL
	 * recursively traverse fTests till we get the names of the tests that are not of class TestSuite
	 * assumes that whenever getTests is called only a suite of TestSuite or a TestSuite is used
	 * ex. AutomatedSuite contains FrameworkTests/TypeTests/etc. or TypeTests
	 * @return a list of tests that are not of the type TestSuite
	 */
	public List<Test> getTests() {
		List<Test> testList = new ArrayList<Test>();
		for (int i = 0; i < fTests.size(); i++) {
			if (fTests.get(i).getClass() == TestSuite.class) 
				getEachTest((TestSuite) fTests.get(i), testList);
			else 
				testList.add(fTests.get(i));
		}
		return Collections.unmodifiableList(testList);
	}
	
	private void getEachTest(TestSuite curr, List<Test> testList) {
		for (int i = 0; i < curr.fTests.size(); i++) {
// not sure if this is needed to recursively get tests
			if (extendsTestSuite(curr, i)) 
				getEachTest((TestSuite) curr.fTests.get(i), testList);
			else 
				testList.add(curr.fTests.get(i));
		}
	}
	
	
	private boolean extendsTestSuite(TestSuite curr, int i) {
		Class currClass = curr.fTests.get(i).getClass();
		
		while (!currClass.equals(Object.class)) {
		
			if (currClass.equals(TestSuite.class))
				return true;
			else 
				currClass = currClass.getSuperclass();
			
		}
		
		return false;
	}
}