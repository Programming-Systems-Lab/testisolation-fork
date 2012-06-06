package edu.washington.cs.dt.premain;

import java.lang.instrument.Instrumentation;

public class Agent {
	
	public static void premain(String agentArgs, Instrumentation inst) {
		final StaticFieldAccessInstrumenter instrumenter = new StaticFieldAccessInstrumenter();
	    inst.addTransformer(instrumenter);
	    
	    //add the shut down hook
	    Runtime.getRuntime().addShutdownHook(new Thread() {
	                @Override
	                public void run() {
	                        //dump the test execution results
	                }
	        });
	}
}
