package org.truffle.cs.lisp.nodes;

import java.util.List;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LispPlusNode extends LispOperandNode {

	public LispPlusNode(FrameDescriptor frameDescriptor) {
		super(frameDescriptor);
	}

	@Override
	public Object execute(VirtualFrame frame, List<LispExpressionNode> list) {
		int res = 0;
		for (LispExpressionNode element : list) {
			res += (int) element.execute(frame);
		}
		return res;
	}

	@Override
	public Object execute(VirtualFrame frame) {
		return null;
	}
}
