package org.truffle.cs.lisp.nodes;

import java.util.Stack;
import java.util.ArrayList;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LispCallableListNode extends LispExpressionNode {
	@Child LispOperationNode operation;
	
	public LispCallableListNode(LispExpressionNode operation, LispExpressionNode arguments[]) {
		if (operation instanceof LispOperationNode){
			this.operation = (LispOperationNode) operation;
			this.operation.setArguments(arguments);
		} else {
			CompilerDirectives.transferToInterpreter();
			if (operation == null) throw new Error("Operation cannot be nil.");
			throw new Error(operation + " must be an operations.");
		}
	}
	
	@Override
	public Object execute(VirtualFrame frame) {
		return operation.execute(frame);
	}
}
