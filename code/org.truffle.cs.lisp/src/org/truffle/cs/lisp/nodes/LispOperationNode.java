package org.truffle.cs.lisp.nodes;

import java.util.List;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class LispOperationNode extends LispExpressionNode {
	@Children 
	protected LispExpressionNode arguments[];
	
	public LispOperationNode() {}
	
	public void setArguments(LispExpressionNode arguments[]) {
		this.arguments = arguments;
	}
}
