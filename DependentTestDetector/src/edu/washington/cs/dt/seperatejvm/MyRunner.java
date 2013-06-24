package edu.washington.cs.dt.seperatejvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class MyRunner extends BlockJUnit4ClassRunner {

	private Class<?> klass;
	private List<String> tests;

	public MyRunner(Class<?> klass, List<String> tests) throws InitializationError {
		super(klass);

		this.klass = klass;
		this.tests = tests;
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		List<FrameworkMethod> m = super.computeTestMethods();		
		if (this.tests != null) {
			HashMap<String, FrameworkMethod> map = new HashMap<>();
			for (FrameworkMethod method : m) {
				map.put(method.getName(), method);
			}

			ArrayList<FrameworkMethod> retval = new ArrayList<>();
			for (String methodName : this.tests) {
				retval.add(map.get(methodName));
			}
			return retval;
		} else {
			return m;
		}
	}

	public List<String> getTestNames() {
		return this.tests;
	}
	
}
