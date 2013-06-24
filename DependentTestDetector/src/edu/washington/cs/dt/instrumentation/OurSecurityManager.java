package edu.washington.cs.dt.instrumentation;

import java.io.File;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.util.HashSet;

public class OurSecurityManager extends SecurityManager {

	static SecurityManager theirMgr = null;
	public static boolean testing = false;

	public static void setSecurityManager(SecurityManager mgr) {
		theirMgr = mgr;
	}

	public static SecurityManager getSecurityManager() {
		return theirMgr;
	}

	@Override
	public void checkPermission(Permission perm) {
		if (theirMgr != null && testing) {
			for (Class<?> klass : this.getClassContext()) {
				if (klass.equals(SecurityManager.class)) {
					return;
				}

				try {
					klass.asSubclass(PrivilegedAction.class);
					//System.out.println("Allowing do priveledged for " + perm + " semantics may have been altered");
					return;
				} catch (ClassCastException ex) {
					// ignore
				}
			}

			try {
				theirMgr.checkPermission(perm);
			} catch (SecurityException ex) {
//				System.err
//						.println("warning.. their security manager threw an exception, semantics may have been altered"
//								+ ex.getMessage());
			}
			super.checkPermission(perm);
		}
		
	}

	public static HashSet<String> reads = new HashSet<>();
	public static HashSet<String> writes = new HashSet<>();
	
	public static void reset() {
		reads = new HashSet<>();
		writes = new HashSet<>();
	}
	
	@Override
	public void checkRead(String file) {
		if (testing) {
			if (!isThisFileCode(file)) { // ignore code files 
				reads.add(file);
			}
		}
		super.checkRead(file);
	}

	private boolean isThisFileCode(String file) {
		return file.endsWith(".class") || file.endsWith(".jar") || file.endsWith(".so") 
				|| file.endsWith(".dll") || file.endsWith(".properties");
	}

	@Override
	public void checkWrite(String file) {
		if (testing) {
			writes.add(file);
		}
		super.checkWrite(file);
	}

	@Override
	public void checkDelete(String file) {
		if (testing) {
			writes.remove(file);
		}
		super.checkDelete(file);
	}
	
	public static void output() {
		for (String read: reads) {
			System.out.println("--- R:" + read);
		}
		for (String write: writes) {
			System.out.println("--- W:" + write);
		}
	}

	/*
	 * @Override public void checkAccept(String host, int port) { if (theirMgr
	 * != null && testing) { theirMgr.checkAccept(host, port); } }
	 * 
	 * @Override public void checkAccess(Thread t) { if (theirMgr != null &&
	 * testing) { theirMgr.checkAccess(t); } }
	 * 
	 * @Override public void checkAccess(ThreadGroup g) { if (theirMgr != null
	 * && testing) { theirMgr.checkAccess(g); } }
	 * 
	 * @Override public void checkAwtEventQueueAccess() { if (theirMgr != null
	 * && testing) { theirMgr.checkAwtEventQueueAccess(); } }
	 * 
	 * @Override public void checkConnect(String host, int port) { if (theirMgr
	 * != null && testing) { theirMgr.checkConnect(host, port); } }
	 * 
	 * @Override public void checkConnect(String host, int port, Object context)
	 * { if (theirMgr != null && testing) { theirMgr.checkConnect(host, port,
	 * context); } }
	 * 
	 * @Override public void checkCreateClassLoader() { if (theirMgr != null &&
	 * testing) { theirMgr.checkCreateClassLoader(); } }
	 * 
	 * @Override public void checkDelete(String file) { if (theirMgr != null &&
	 * testing) { theirMgr.checkDelete(file); } }
	 * 
	 * @Override public void checkExec(String cmd) { if (theirMgr != null &&
	 * testing) { theirMgr.checkExec(cmd); } }
	 * 
	 * @Override public void checkExit(int status) { if (theirMgr != null &&
	 * testing) { theirMgr.checkExit(status); } }
	 * 
	 * @Override public void checkLink(String lib) { if (theirMgr != null &&
	 * testing) { theirMgr.checkLink(lib); } }
	 * 
	 * @Override public void checkListen(int port) { if (theirMgr != null &&
	 * testing) { theirMgr.checkListen(port); } }
	 * 
	 * @Override public void checkMemberAccess(Class<?> clazz, int which) { if
	 * (theirMgr != null && testing) { theirMgr.checkMemberAccess(clazz, which);
	 * } }
	 * 
	 * @Override public void checkMulticast(InetAddress maddr) { if (theirMgr !=
	 * null && testing) { theirMgr.checkMulticast(maddr); } }
	 * 
	 * @Override public void checkPackageAccess(String pkg) {
	 * //System.out.println("---- " + pkg); if
	 * (pkg.startsWith("junit.framework")) { return; } if (theirMgr != null &&
	 * testing) { System.out.println("Delegating");
	 * theirMgr.checkPackageAccess(pkg); } }
	 * 
	 * @Override public void checkPackageDefinition(String pkg) { if (theirMgr
	 * != null && testing) { theirMgr.checkPackageDefinition(pkg); } }
	 * 
	 * @Override public void checkPermission(Permission perm) { if (theirMgr !=
	 * null && testing) { theirMgr.checkPermission(perm); } }
	 * 
	 * @Override public void checkPermission(Permission perm, Object context) {
	 * if (theirMgr != null && testing) { theirMgr.checkPermission(perm,
	 * context); } }
	 * 
	 * @Override public void checkPrintJobAccess() { if (theirMgr != null &&
	 * testing) { theirMgr.checkPrintJobAccess(); } }
	 * 
	 * @Override public void checkPropertiesAccess() { if (theirMgr != null &&
	 * testing) { theirMgr.checkPropertiesAccess(); } }
	 * 
	 * @Override public void checkPropertyAccess(String key) { if (theirMgr !=
	 * null && testing) { theirMgr.checkPropertyAccess(key); } }
	 * 
	 * @Override public void checkRead(FileDescriptor fd) { if (theirMgr != null
	 * && testing) { theirMgr.checkRead(fd); } }
	 * 
	 * @Override public void checkRead(String file) { if (theirMgr != null &&
	 * testing) { theirMgr.checkRead(file); } }
	 * 
	 * @Override public void checkRead(String file, Object context) { if
	 * (theirMgr != null && testing) { theirMgr.checkRead(file, context); } }
	 * 
	 * @Override public void checkSecurityAccess(String target) { if (theirMgr
	 * != null && testing) { theirMgr.checkSecurityAccess(target); } }
	 * 
	 * @Override public void checkSetFactory() { if (theirMgr != null &&
	 * testing) { theirMgr.checkSetFactory(); } }
	 * 
	 * @Override public void checkSystemClipboardAccess() { if (theirMgr != null
	 * && testing) { theirMgr.checkSystemClipboardAccess(); } }
	 * 
	 * @Override public void checkWrite(FileDescriptor fd) { if (theirMgr !=
	 * null && testing) { theirMgr.checkWrite(fd); } }
	 * 
	 * @Override public void checkWrite(String file) { if (theirMgr != null &&
	 * testing) { theirMgr.checkWrite(file); } }
	 */
}