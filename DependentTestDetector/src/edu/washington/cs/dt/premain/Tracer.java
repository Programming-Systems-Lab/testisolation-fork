package edu.washington.cs.dt.premain;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class Tracer {
	
	public static Collection<String> accessedFields = new HashSet<String>();
	
	public static void traceField(String field) {
		accessedFields.add(field);
	}
	
	public static Collection<String> getAccessedFields() {
		return accessedFields;
	}

}
