package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;

@NodeInfo
public class MJFunction extends RootNode {

    final String name;

    public MJFunction(String name, FrameDescriptor frameDescriptor) {
        super(null, frameDescriptor);
        this.name = name;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        System.out.println("Do sth....TODO student :-) ");
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
