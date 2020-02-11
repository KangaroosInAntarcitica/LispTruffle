package org.truffle.cs.lisp.nodes;

import java.util.List;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class LispArithmeticNode extends LispOperationNode {
	private Object accumulator;
	
	public abstract Object reduce(Object accumulator, Object value);
	
	@Override
	public Object execute(VirtualFrame frame) {
		accumulator = arguments.get(0).execute(frame);
		
		// TODO add checks
		for (int i = 1; i < arguments.size(); i++) {
			accumulator = reduce(accumulator, arguments.get(i).execute(frame));
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
