package org.truffle.cs.lisp.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class LispOperandNode extends LispExpressionNode {

	protected LispOperandNode(FrameDescriptor frameDescriptor) {
		super(frameDescriptor);
	}
	
	public abstract Object execute(VirtualFrame frame, List<LispExpressionNode> list);
}
