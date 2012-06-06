package edu.washington.cs.dt.premain;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class StaticFieldAccessInstrumenter
    extends AbstractTransformer implements ClassFileTransformer, Opcodes {

	@Override
	protected void transformClassNode(ClassNode cn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		// TODO Auto-generated method stub
		return null;
	}

}
