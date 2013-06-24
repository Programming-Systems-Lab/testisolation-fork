/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */
package edu.washington.cs.dt.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;

import junit.framework.TestFailure;

public class CodeUtils {
	public static final String TESTCASE = "junit.framework.TestCase";

	public static boolean hasDefaultConstructor(Class<?> clz) {
		boolean has = false;
		Constructor<?>[] cs = clz.getConstructors();
		for (Constructor<?> c : cs) {
			if (c.getParameterTypes().length == 0) {
				has = true;
				break;
			}
		}
		return has;
	}

	public static boolean isJUnit3Class(Class<?> clz) {
		Class<?> superClass = clz.getSuperclass();
		if (superClass == null) {
			return false;
		}
		if (superClass.getName().equals(TESTCASE)) {
			return true;
		}
		while (superClass.getSuperclass() != null) {
			superClass = superClass.getSuperclass();
			if (superClass.getName().equals(TESTCASE)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isJUnit4XMethod(Method method) {
		org.junit.Test testAnn = method.getAnnotation(org.junit.Test.class);
		org.junit.Ignore ignoreAnn = method
				.getAnnotation(org.junit.Ignore.class);
		return testAnn != null && ignoreAnn == null;
	}

	public static boolean isJUnit3XMethod(Method method) {
		return Modifier.isPublic(method.getModifiers()) // &&
														// !Modifier.isStatic(method.getModifiers())
				&& method.getName().startsWith("test")
				&& method.getParameterTypes().length == 0
				&& method.getReturnType().equals(void.class);
	}

	public static boolean isInstantiableJUnitClass(Class<?> c) {
		return true;
	}

	public static String pathToClass(File f, String dir) {
		String absoluteFilePath = f.getAbsolutePath();
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		if (!absoluteFilePath.startsWith(dir)
				|| !absoluteFilePath.endsWith(".class")) {
			throw new RuntimeException("File: " + f + " must start with: "
					+ dir + ". Or it is not a java file.");
		}
		String clazzName = absoluteFilePath.substring(dir.length(),
				absoluteFilePath.length() - ".class".length());

		return clazzName.replace(File.separatorChar, '.');
	}

	public static String flattenFailrues(Enumeration<TestFailure> failIt) {
		StringBuilder sb = new StringBuilder();
		while (failIt.hasMoreElements()) {
			sb.append(failIt.nextElement());
			sb.append(Globals.lineSep);
		}
		return sb.toString();
	}

	public static boolean useJUnit4(String fullTestName) {
		String clzName = fullTestName.substring(0,
				fullTestName.lastIndexOf("."));
		try {
			Class<?> clz = Class.forName(clzName);
			boolean isJUnit3Class = isJUnit3Class(clz);
			return !isJUnit3Class;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	// this is useless
	public static String getStaticValue(String className, String fieldName) {
		try {
			Class<?> clz = CodeUtils.class.getClassLoader()
					.loadClass(className);
			Field f = clz.getField(fieldName);
			Object v = f.get(null);
			return v == null ? null : v.toString();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	public static String getClassNameFromMethodName(String methodName) {
		int pos = methodName.lastIndexOf('.');
		return methodName.substring(0, pos);
	}

	public static String getMethodNameFromMethodName(String methodName) {
		int pos = methodName.lastIndexOf('.');
		return methodName.substring(pos + 1);
	}

	public static Class<?> forName(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Method getMethod(Class<?> klass, String methodName) {
		try {
			return klass.getMethod(methodName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Field getField(String field) throws NoSuchFieldException {
		String className = getClassNameFromMethodName(field);
		String fieldName = getMethodNameFromMethodName(field);
		
		try {
			Class<?> klass = Class.forName(className);
			while (true) {
				try {
					return klass.getDeclaredField(fieldName);
				} catch (NoSuchFieldException ex) {
					klass = klass.getSuperclass();
					if (Object.class == klass) {
						throw new NoSuchFieldException(); 
					}
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isPrimitiveOrImmutable(Class<?> klass) {
		return klass.isPrimitive() || String.class.equals(klass) || Boolean.class.equals(klass) 
				|| Integer.class.equals(klass) || Class.class.isAssignableFrom(klass); // TODO: maybe more boxed classes
	}
}