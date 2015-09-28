package edu.washington.cs.dt.premain;

import java.lang.instrument.Instrumentation;
import java.util.Collection;
import java.util.LinkedList;

import edu.washington.cs.dt.util.Files;
import edu.washington.cs.dt.util.Globals;

public class Agent {
	
	
	public static String fileDir = "." + Globals.fileSep + "output";
	
	static
	{
		String outputDir = System.getProperty("outputDir");
		if(outputDir != null)
		{
			fileDir = outputDir;
		}
	}
	public static Thread shutdownHook;
	public static void premain(String agentArgs, Instrumentation inst) {
		final StaticFieldAccessInstrumenter instrumenter = new StaticFieldAccessInstrumenter();
	    inst.addTransformer(instrumenter);
//		final Instrumenter instrumenter = new Instrumenter();
//	    inst.addTransformer(instrumenter);

	    
	    String writeFileName = agentArgs != null ? agentArgs : null;
	    final String writeFileFullPath = fileDir + Globals.fileSep + writeFileName + ".txt";
	    
	    //add the shut down hook
	    shutdownHook = (new Thread() {
	                @Override
	                public void run() {
	                       if(writeFileFullPath != null) {
	                    	   Files.createIfNotExistNoExp(writeFileFullPath);
	                    	   Collection<String> fields = Tracer.getAccessedFields();
	                    	   Tracer.accessedFields = new LinkedList<String>();
	                    	   Files.writeToFileWithNoExp(fields, writeFileFullPath);
	                       }
	                }
	        });
	    Runtime.getRuntime().addShutdownHook(shutdownHook);
	}
}
