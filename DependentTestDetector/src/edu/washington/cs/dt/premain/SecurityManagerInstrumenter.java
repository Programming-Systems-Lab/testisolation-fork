package edu.washington.cs.dt.premain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.List;

import edu.washington.cs.dt.org.objectweb.asm.Opcodes;
import edu.washington.cs.dt.org.objectweb.asm.tree.AbstractInsnNode;
import edu.washington.cs.dt.org.objectweb.asm.tree.ClassNode;
import edu.washington.cs.dt.org.objectweb.asm.tree.FieldInsnNode;
import edu.washington.cs.dt.org.objectweb.asm.tree.InsnList;
import edu.washington.cs.dt.org.objectweb.asm.tree.LdcInsnNode;
import edu.washington.cs.dt.org.objectweb.asm.tree.MethodInsnNode;
import edu.washington.cs.dt.org.objectweb.asm.tree.MethodNode;
import edu.washington.cs.dt.util.Log;

public class SecurityManagerInstrumenter
    extends AbstractTransformer implements ClassFileTransformer, Opcodes {

	public static boolean verbose = false;
	
	@Override
	protected void transformClassNode(ClassNode cn) {
		List<MethodNode> methods = cn.methods;
        for (MethodNode method : methods) {
                if ((method.access & ACC_NATIVE) > 0
                                || (method.access & ACC_ABSTRACT) > 0) {
                        continue;
                }
                //skip constructors
                if(InstrumentUtils.isClinit(method) || InstrumentUtils.isInit(method)) {
                	continue;
                }
                //skip those proxy classes created by JVM
                if(cn.name.startsWith("$Proxy")) {
                	continue;
                }
                if(verbose) {
                    System.out.println("transforming: " + cn.name + "#" + method.name);
                }
                this.instrumentMethod(cn, method);
        }
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		if (!InstrumentUtils.shouldInstrumentThisClass(
				InstrumentUtils.transClassNameDotToSlash(className))) {
        return null;
        }

		try {
			// return instrumented class
			return this.treeAPITransform(classfileBuffer);
		} catch (Exception e) {
			Log.logln("Errors in transforming: " + className);
			return null; //return null means no transformation
		}
	}
	
	/**
     * Instrument each method node in class
     * */
	private static String tracerClass = "edu/washington/cs/dt/premain/Tracer";
	private static String tracerMethod = "traceField";
    private void instrumentMethod(ClassNode cn, MethodNode method) {
            InsnList mlist = method.instructions;
            if (mlist.size() == 0) {
                    return;
            }
            int instructionIndex = 0; 
            for(instructionIndex = 0; instructionIndex < mlist.size(); instructionIndex++) {
            	AbstractInsnNode inst = mlist.get(instructionIndex);
            	if (inst instanceof MethodInsnNode) {
            		MethodInsnNode methodNode = (MethodInsnNode) inst;
            		//System.out.println(methodNode.owner + " " + methodNode.name);
            		if ("java/lang/System".equals(methodNode.owner) && ( "setSecurityManager".equals(methodNode.name) || "getSecurityManager".equals(methodNode.name))) {
            			System.out.println("Instrumented: " + inst);
            			methodNode.owner = "edu/washington/cs/dt/instrumentation/OurSecurityManager";
            		}
            	}
            }                  
    }
}
