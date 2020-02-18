package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class LispArithmeticNode extends LispExpressionNode {
	private Object accumulator;
	
	public abstract Object reduce(Object accumulator, Object value);
	
	@Override
	public Object execute(VirtualFrame frame) {
		Object[] arguments = frame.getArguments();

		if (arguments.length > 1) {
			accumulator = arguments[1];
		} else {
			CompilerDirectives.transferToInterpreter();
			throw new Error("Operation must be have at least one argument.");
		}

		for (int i = 2; i < arguments.length; i++) {
			accumulator = reduce(accumulator, arguments[i]);
		}

		return accumulator;
	}
	
	public static class LispAddNode extends LispArithmeticNode {
		@Override
		public Object reduce(Object accumulator, Object value) {
			return (int) accumulator + (int) value;
		}
	}
	
	public static class LispSubtractNode extends LispArithmeticNode {
		@Override
		public Object reduce(Object accumulator, Object value) {
			return (int) accumulator - (int) value;
		}
	}
	
	public static class LispMultiplyNode extends LispArithmeticNode {
		@Override
		public Object reduce(Object accumulator, Object value) {
			return (int) accumulator * (int) value;
		}
	}
	
	public static class LispDivideNode extends LispArithmeticNode {
		@Override
		public Object reduce(Object accumulator, Object value) {
			return (int) accumulator / (int) value;
		}
	}
}
