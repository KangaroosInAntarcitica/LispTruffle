package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LispIntNode extends LispExpressionNode {

	private final int val;
	
	public LispIntNode(FrameDescriptor frameDescriptor, int val) {
		super(frameDescriptor);
		this.val = val;
	}

	@Override
	public Object execute(VirtualFrame frame) {
		return val;
	}
	
}
