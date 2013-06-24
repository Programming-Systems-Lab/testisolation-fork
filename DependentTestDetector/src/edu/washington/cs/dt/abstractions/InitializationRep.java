package edu.washington.cs.dt.abstractions;

import java.lang.reflect.Method;

import junit.extensions.TestSetup;

public class InitializationRep {


	public TestSetup setup;

	public InitializationRep(TestSetup setup) {
		this.setup = setup;
	}

	public TestSetup getTest() {
		return setup;
	}
	
	public void setUp() {
		callMethod("setUp");
	}
	
	public void tearDown() {
		callMethod("tearDown");
	}

	
	private void callMethod(String methodName) {
		Method method;
		try {
			Class <?> searchingIn = setup.getClass();
			while (true) {
				try {
					method = searchingIn.getDeclaredMethod(methodName);
					method.setAccessible(true);
					method.invoke(setup);
					break;
				} catch (NoSuchMethodException ex) {
					searchingIn = searchingIn.getSuperclass();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
