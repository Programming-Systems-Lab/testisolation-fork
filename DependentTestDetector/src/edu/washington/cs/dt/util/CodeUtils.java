package edu.washington.cs.dt.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CodeUtils {
	public static final String TESTCASE = "junit.framework.TestCase";
	
	public static boolean hasDefaultConstructor(Class<?> clz) {
		boolean has = false;
		Constructor<?>[] cs = clz.getConstructors();
		for(Constructor<?> c : cs) {
			if(c.getParameterTypes().length == 0) {
				has = true;
				break;
			}
		}
		return has;
	}
	
	public static boolean isJUnitClass(Class<?> clz) {
		Class<?> superClass = clz.getSuperclass();
		if(superClass == null) {
			return false;
		}
		if(superClass.getName().equals(TESTCASE)) {
			return true;
		}
		while(superClass.getSuperclass() != null) {
			superClass = superClass.getSuperclass();
			if(superClass.getName().equals(TESTCASE)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isJUnitMethod(Method method) {
		return Modifier.isPublic(method.getModifiers()) //&& !Modifier.isStatic(method.getModifiers())
		    && method.getName().startsWith("test") && method.getParameterTypes().length == 0
		    && method.getReturnType().equals(void.class);
	}
	
	public static String pathToClass(File f, String dir) {
		String absoluteFilePath = f.getAbsolutePath();
		if(!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		if(!absoluteFilePath.startsWith(dir) || !absoluteFilePath.endsWith(".java")) {
			throw new RuntimeException("File: " + f + " must start with: " + dir
					+ ". Or it is not a java file.");
		}
		String clazzName = absoluteFilePath.substring(dir.length(),
				absoluteFilePath.length() - ".java".length());
		
	    return clazzName.replace(File.separatorChar, '.');
	}
}