package edu.washington.cs.dt.instrumentation;

import java.lang.ref.WeakReference;

public class OurWeakReference {

	private WeakReference<Object> weakRef;
	private int hashCode;
	

	public OurWeakReference(Object obj) {
		this.weakRef = new WeakReference<>(obj);
		this.hashCode = System.identityHashCode(obj);
	}
	
	@Override
	public int hashCode() {
		return this.hashCode;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !(other  instanceof OurWeakReference)) {
			return false;
		}
		OurWeakReference otherwr = (OurWeakReference) other;
		return otherwr.weakRef.get() == this.weakRef.get();
	}
}
