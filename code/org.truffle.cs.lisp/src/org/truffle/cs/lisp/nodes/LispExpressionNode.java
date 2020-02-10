package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.RootNode;

public abstract class LispExpressionNode extends RootNode {
	
	protected LispExpressionNode(FrameDescriptor frameDescriptor) {
		super(null, frameDescriptor);
	}
}
