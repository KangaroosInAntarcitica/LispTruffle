package org.truffle.cs.mj.nodes;


import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo
public abstract class MJStatementNode extends Node {
    public abstract Object execute(VirtualFrame frame);
}
