package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeInfo
public class LispRootNode extends RootNode {
	@Child LispExpressionNode body;

	public LispRootNode(LispExpressionNode body, FrameDescriptor frameDescriptor) {
		super(null, frameDescriptor);
		this.body = body;
	}
	
	@Override
    public Object execute(VirtualFrame frame) {
        return body.execute(frame);
    }
}
