package org.truffle.cs.lisp.nodes;

import java.util.ArrayList;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LispListNode extends LispExpressionNode {
	
	private ArrayList<LispExpressionNode> elements;
	
	public LispListNode(FrameDescriptor frameDescriptor) {
		super(frameDescriptor);
		elements = new ArrayList<LispExpressionNode>();
	}

	public boolean addElement(LispExpressionNode element) {
		return elements.add(element);
	}
	
	@Override
	public Object execute(VirtualFrame frame) {
		if (elements.get(0) instanceof LispOperandNode) {
			return ((LispOperandNode) elements.get(0)).execute(frame, elements.subList(1, elements.size()));
		} else {
			throw new Error(elements.get(0) + " must be an operand.");
		}
	}
}
