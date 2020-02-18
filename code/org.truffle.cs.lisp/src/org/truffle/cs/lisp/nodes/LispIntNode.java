package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo
public class LispIntNode extends LispExpressionNode {
	private final int value;
	
	public LispIntNode(int value) {
		this.value = value;
	}

	@Override
	public Object execute(VirtualFrame frame) {
		return value;
	}

}
