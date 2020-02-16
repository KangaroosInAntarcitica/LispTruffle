package org.truffle.cs.lisp.nodes;

import java.util.List;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

public abstract class LispArithmeticNode extends LispOperationNode {
	private Object accumulator;
	protected float defaultAccumulator;
	
	public abstract Object reduce(Object accumulator, Object value);
	
	@Override
	@ExplodeLoop()
	public Object execute(VirtualFrame frame) {
		if (arguments.length == 0) {
			CompilerDirectives.transferToInterpreter();
			throw new Error("Operation must be have at least one argument.");
		}

		int start;
		if (arguments.length == 1) {
			accumulator = defaultAccumulator;
			start = 0;
		} else {
			accumulator = arguments[0].execute(frame);
			start = 1;
		}

		// TODO add checks

		for (int i = start; i < arguments.length; i++) {
			accumulator = reduce(accumulator, arguments[i].execute(frame));
		}
		
		return accumulator;
	}
	
	public static class LispAddNode extends LispArithmeticNode {
		public LispAddNode() {
			defaultAccumulator = 0;
		}

		@Override
		public Object reduce(Object accumulator, Object value) {
			return (float) accumulator + (float) value;
		}
	}
	
	public static class LispSubtractNode extends LispArithmeticNode {
		public LispSubtractNode() {
			defaultAccumulator = 0;
		}

		@Override
		public Object reduce(Object accumulator, Object value) {
			return (float) accumulator - (float) value;
		}
	}
	
	public static class LispMultiplyNode extends LispArithmeticNode {
		public LispMultiplyNode() {
			defaultAccumulator = 1;
		}

		@Override
		public Object reduce(Object accumulator, Object value) {
			return (float) accumulator * (float) value;
		}
	}
	
	public static class LispDivideNode extends LispArithmeticNode {
		public LispDivideNode() {
			defaultAccumulator = 1;
		}

		@Override
		public Object reduce(Object accumulator, Object value) {
			return (float) accumulator / (float) value;
		}
	}
}
