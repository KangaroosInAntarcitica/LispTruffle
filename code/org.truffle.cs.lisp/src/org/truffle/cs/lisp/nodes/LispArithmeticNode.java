package org.truffle.cs.lisp.nodes;

import java.util.List;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

public abstract class LispArithmeticNode extends LispOperationNode {
	private Object accumulator;
	
	public abstract Object reduce(Object accumulator, Object value);
	
	@Override
	@ExplodeLoop()
	public Object execute(VirtualFrame frame) {
		if (arguments.length > 0) {
			accumulator = arguments[0].execute(frame);
		} else {
			CompilerDirectives.transferToInterpreter();
			throw new Error("Operation must be have at least one argument.");
		}
		
		// TODO add checks
		for (int i = 1; i < arguments.length; i++) {
			accumulator = reduce(accumulator, arguments[i].execute(frame));
		}
		
		return accumulator;
	}
	
	public static class LispAddNode extends LispArithmeticNode {
		@Override
		public Object reduce(Object accumulator, Object value) {
			return (float) accumulator + (float) value;
		}
	}
	
	public static class LispSubtractNode extends LispArithmeticNode {
		@Override
		public Object reduce(Object accumulator, Object value) {
			return (float) accumulator - (float) value;
		}
	}
	
	public static class LispMultiplyNode extends LispArithmeticNode {
		@Override
		public Object reduce(Object accumulator, Object value) {
			return (float) accumulator * (float) value;
		}
	}
	
	public static class LispDivideNode extends LispArithmeticNode {
		@Override
		public Object reduce(Object accumulator, Object value) {
			return (float) accumulator / (float) value;
		}
	}
}
