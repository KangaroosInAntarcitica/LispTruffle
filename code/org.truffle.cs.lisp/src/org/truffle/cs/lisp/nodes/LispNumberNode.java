package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeInfo
public class LispNumberNode extends LispExpressionNode {

	private final float value;
	
	public LispNumberNode(float value) {
		this.value = value;
	}

	@Override
	public Object execute(VirtualFrame frame) {
		return value;
	}

}
