package org.truffle.cs.mj.nodes;


import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo
public class MJReadParameterNode extends MJExpressionNode {
    final int index;

    public MJReadParameterNode(int index) {
        this.index = index;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return frame.getArguments()[index];
    }

}
