package edu.washington.cs.dt.util;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class Utils {
	
	public static String listToStr(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for(String s : list) {
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public static void checkTrue(boolean condition, String message) {
		if(!condition) {
			throw new RuntimeException(message);
		}
	}
	
	public static void checkNull(Object o, String message) {
		if(o == null) {
			throw new RuntimeException(message);
		}
	}
	
	public static boolean VERBOSE = true;
	public static void stdln(String str) {
		if(!VERBOSE)
			return;
		System.out.println(str);
	}
	
	public static void std(String str) {
		if(!VERBOSE)
			return;
		System.out.print(str);
	}
	
	public static void stdln(Object obj) {
		if(!VERBOSE)
			return;
		System.out.println(obj);
	}
	
	public static void std(Object obj) {
		if(!VERBOSE)
			return;
		System.out.print(obj);
	}
	
	public static<T> List<T> createList(T...ints) {
		List<T> list = new LinkedList<T>();
		for(T i : ints) {
			list.add(i);
		}
		return list;
	}
	
	public static String convertArrayToFlatString(Object array) {
	      checkNull(array, "The array object could not be null");
	      checkTrue(array.getClass().isArray(), "The give object: " + array  + "is not an array type.");
	      StringBuilder sb = new StringBuilder();
	      
	      int length = Array.getLength(array);
	      sb.append("[");
	      for(int i = 0; i < length; i++) {
	        Object obj = Array.get(array, i);
	        if(obj == null) {
	          sb.append("null");
	        } else {
	          sb.append(obj);
	        }
	        if(i != length - 1) {
	          sb.append(", ");
	        }
	      }
	      sb.append("]");
	      
	      return sb.toString();
	    }
}