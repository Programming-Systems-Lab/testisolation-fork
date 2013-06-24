package edu.washington.cs.dt.instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;


import edu.washington.cs.dt.util.CodeUtils;



public class OurTracer {
	
	public static boolean testing = false;
	public static HashSet<OurWeakReference> newed = new HashSet<>();
	
	public static HashMap<OurWeakReference,HashSet<String>> reads = new HashMap<>();
	public static HashMap<OurWeakReference,HashSet<String>> writes = new HashMap<>();
	
	public static void reset() {
		newed = new HashSet<>();
		reads = new HashMap<>();
		writes = new HashMap<>();
	}
	
	public static void traceNew(Object obj) {
		if (! testing) {
			return;
		}
		OurWeakReference key = new OurWeakReference(obj);
		newed.add(key);
		reads.remove(key);
		writes.remove(key);
		//System.out.println("NEW: " + Integer.toHexString(System.identityHashCode(key)));
	}

	private static void testForRead(String str, OurWeakReference key) {
		if (getReads(key).contains(str) || getWrites(key).contains(str)) {
			
		} else {
			getReads(key).add(str);
		}
	}
	
	private static void testForWrite(String str, OurWeakReference key) {
		if (getWrites(key).contains(str)) {
			
		} else {
			getWrites(key).add(str);
			//System.out.println("Write: " + str + " (" + Integer.toHexString(System.identityHashCode(key)) + ")");
			/*Throwable throwable = new Throwable();
			for (StackTraceElement ste: throwable.getStackTrace()) {
				if (ste.getMethodName().equals("<clinit>")) {
					return;
				}
			}
			throwable.printStackTrace(System.out);*/
		}
	}
	
	private static HashSet<String> getReads(OurWeakReference key) {
		if (! reads.containsKey(key)) {
			reads.put(key, new HashSet<String>());
		}
		return reads.get(key);
	}
	
	private static HashSet<String> getWrites(OurWeakReference key) {
		if (! writes.containsKey(key)) {
			writes.put(key, new HashSet<String>());
		}
		return writes.get(key);
	}

	public static OurWeakReference staticKey = new OurWeakReference(new Object());
	
	
	public static void traceReadStatic(String field) {
		if (! testing) {
			return;
		}
		testForRead(field, staticKey);
	}
	
	public static void traceWriteStatic(String field) {
		if (! testing) {
			return;
		}
		testForWrite(field, staticKey);
	}
	
	public static void traceReadField(Object obj, String field) {
		if (! testing) {
			return;
		}
		OurWeakReference key =  new OurWeakReference(obj);
		if (! newed.contains(key)) { 
			testForRead(field, key);
		}
	}
	
	public static void traceWriteField(Object obj, String field) {
		if (! testing) {
			return;
		}
		OurWeakReference key =  new OurWeakReference(obj);
		if (! newed.contains(key)) { 
			testForWrite(field, key);
		}
	}
	
	public static void writeOutput() {
		HashSet<String> uniqueReads = new HashSet<String>();
		HashSet<String> uniqueWrites = new HashSet<String>();
		
		for (Entry<OurWeakReference, HashSet<String>> item : reads.entrySet()) {
			for (String field : item.getValue()) {
				uniqueReads.add(field);
			}
		}
		
		for (Entry<OurWeakReference, HashSet<String>> item : writes.entrySet()) {
			for (String field : item.getValue()) {
				uniqueWrites.add(field);
			}
		}
		
		for (String read : uniqueReads) {
			try {
				Field f = CodeUtils.getField(read);
				if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())
						&& CodeUtils.isPrimitiveOrImmutable(f.getType())) {
					//System.out.println("--- R:" + read);
					continue;
				}
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
			System.out.println("+++ R:" + read);
		}
		for (String write: uniqueWrites) {
			System.out.println("+++ W:" + write);
		}
	}
}
