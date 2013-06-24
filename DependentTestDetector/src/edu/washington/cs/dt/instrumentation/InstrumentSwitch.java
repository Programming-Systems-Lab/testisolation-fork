package edu.washington.cs.dt.instrumentation;

public class InstrumentSwitch {

	public static boolean instrument = true;
	
	public static void start() {
		if (instrument) {
			OurTracer.reset();
			OurTracer.testing = true;
			OurSecurityManager.reset();
			OurSecurityManager.testing = true;
		}
	}
	
	public static void stop(String lastResult) {
		if (instrument) {
			OurTracer.testing = false;
			OurTracer.writeOutput();
			OurSecurityManager.testing = false;
			OurSecurityManager.output();
		}
		
		System.out.println(lastResult);
	}
}
