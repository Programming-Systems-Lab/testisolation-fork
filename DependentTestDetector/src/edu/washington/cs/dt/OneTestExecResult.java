package edu.washington.cs.dt;

import edu.washington.cs.dt.util.Utils;

public class OneTestExecResult {
	
	public final RESULT result;
	
	public final String exceptionStackTrace;
	
	public OneTestExecResult(RESULT result) {
		this(result, null);
	}
	
	public OneTestExecResult(RESULT result, String stackTrace) {
		Utils.checkNull(result, "result can not be null.");
		this.result = result;
		this.exceptionStackTrace = stackTrace;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof OneTestExecResult) {
			OneTestExecResult r = (OneTestExecResult)o;
			return r.result.equals(this.result)
			    && (this.exceptionStackTrace != null
			    		? this.exceptionStackTrace.equals(r.exceptionStackTrace)
			    				: r.exceptionStackTrace == null);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 13*result.hashCode() + (this.exceptionStackTrace != null ? 29*this.exceptionStackTrace.hashCode() : 9);
	}
}