package edu.washington.cs.dt.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


public class MonitoringPrintStream extends PrintStream {

	ByteArrayOutputStream filtered = new ByteArrayOutputStream();
	PrintStream psFiltered = new PrintStream(filtered);
	ProgressCallback callback = null;
	
	public MonitoringPrintStream(OutputStream out, boolean autoFlush) {
		super(out, autoFlush);
	}

	@Override
	public void print(char c) {
		// TODO Auto-generated method stub
		super.print(c);
	}

	@Override
	public void println() {
		super.println();
	}
	
	@Override
	public void println(String x) {
		if (x.startsWith(Globals.stdoutPrefix)) {
			psFiltered.println(x.substring(Globals.stdoutPrefix.length()));
		}
		if (x.startsWith(Globals.stdoutProgressPrefix)) {
			if (callback != null) {				
				callback.testComplete();
			}
		}
		super.println(x);
	}
	
	@Override
	public void close() {
		super.close();
		try {
			filtered.close();
		} catch (IOException e) {
		}
		psFiltered.close();
	}
	
	public String getFilteredLines() {
		return filtered.toString();
	}
	
	
	public void setCallback(ProgressCallback callback) {
		this.callback = callback;
	}
}
