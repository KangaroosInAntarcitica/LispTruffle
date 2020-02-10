package org.truffle.cs.lisp.nodes;

import java.util.List;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class LispOperationNode extends LispExpressionNode {
	protected List<LispExpressionNode> arguments;
	
	public LispOperationNode() {}
	
	public void setArguments(List<LispExpressionNode> arguments) {
		this.arguments = arguments;
	}
}
