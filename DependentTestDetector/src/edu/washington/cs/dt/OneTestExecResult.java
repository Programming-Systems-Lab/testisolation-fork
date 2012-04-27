/** Copyright 2012 University of Washington. All Rights Reserved.
 *  @author Sai Zhang
 */ 
package edu.washington.cs.dt;

import edu.washington.cs.dt.main.Main;
import edu.washington.cs.dt.util.Utils;

public class OneTestExecResult {
	
	public final RESULT result;
	
	public final String exceptionStackTrace;
	
	private final boolean comparestacktrace;
	
	public OneTestExecResult(RESULT result) {
		this(result, null);
	}
	
	public OneTestExecResult(RESULT result, String stackTrace) {
		Utils.checkNull(result, "result can not be null.");
		this.result = result;
		this.exceptionStackTrace = stackTrace;
		this.comparestacktrace = Main.comparestacktrace;
	}
	
	@Override
	public String toString() {
		return this.result.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof OneTestExecResult)) {
			return false;
		}
		
		OneTestExecResult r = (OneTestExecResult)o;
		
		if(!comparestacktrace) {
			return r.result.equals(this.result);
		} else {
		    return r.result.equals(this.result)
		        && (this.exceptionStackTrace != null
		    		? this.exceptionStackTrace.equals(r.exceptionStackTrace)
		    		: r.exceptionStackTrace == null);
		}
	}
	
	@Override
	public int hashCode() {
		if(!comparestacktrace) {
			return 13*result.hashCode();
		}
		
		return 13*result.hashCode() + (this.exceptionStackTrace != null ? 29*this.exceptionStackTrace.hashCode() : 9);
	}
}