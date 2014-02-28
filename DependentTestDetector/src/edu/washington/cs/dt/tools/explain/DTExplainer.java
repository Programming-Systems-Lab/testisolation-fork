package edu.washington.cs.dt.tools.explain;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import plume.Pair;

import edu.washington.cs.dt.tools.traceanalysis.FieldAccess;
import edu.washington.cs.dt.tools.traceanalysis.TestExecTraceReader;
import edu.washington.cs.dt.util.Globals;

public class DTExplainer {
	
	static String TRACE = "_trace.txt";

	public final String traceFolder;
	public final String depTest;
	public final List<String> depSeqs;
	
	public DTExplainer(String folder, String depTest, Collection<String> depSeqs) {
		this.traceFolder = folder;
		this.depTest = depTest;
		this.depSeqs = new ArrayList<String>();
		this.depSeqs.addAll(depSeqs);
	}
	
	//we need to run the test in two orders
	//only focus on fields read by both orders
	public void identifyConflictAccess() {
		TestExecTraceReader reader = new TestExecTraceReader();
		String depTestTraceFile = traceFolder + Globals.fileSep + depTest + TRACE;
		Pair<Set<FieldAccess>, Set<FieldAccess>> rwPairs = reader.readFieldAccessInfo(new File(depTestTraceFile));
		
		if(depSeqs.size() > 1) {
			throw new Error("Not implemented.");
		}
		String causeTestTraceFile = traceFolder + Globals.fileSep + depSeqs.get(0) + TRACE;
		Pair<Set<FieldAccess>, Set<FieldAccess>> causeRWPairs = reader.readFieldAccessInfo(new File(causeTestTraceFile));
		
		for(FieldAccess read : rwPairs.a) {
			if(causeRWPairs.b.contains(read)) {
				System.out.println("conflicts: " + read);
			}
		}
	}
	
}