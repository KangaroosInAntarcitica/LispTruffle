package org.truffle.cs.lisp.nodes;

import java.util.Stack;
import java.util.ArrayList;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LispCallableListNode extends LispExpressionNode {
	@Child LispOperationNode function;
	
	public LispCallableListNode(ArrayList<LispExpressionNode> expressions) {
		if (expressions.get(0) instanceof LispOperationNode){
			this.function = (LispOperationNode) expressions.get(0);
			this.function.setArguments(expressions.subList(1, expressions.size()));
		} else {
			CompilerDirectives.transferToInterpreter();
			throw new Error(expressions.get(0) + " must be an operations.");
		}
	}
	
	@Override
	public Object execute(VirtualFrame frame) {
		return function.execute(frame);
	}
}
