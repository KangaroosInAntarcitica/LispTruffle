package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeInfo
public abstract class LispBoolNode extends LispExpressionNode {
    public static class TRUE extends LispBoolNode {
        @Override
        public Object execute(VirtualFrame frame) {
            return Boolean.TRUE;
        }
    }

    public static class FALSE extends LispBoolNode {
        @Override
        public Object execute(VirtualFrame frame) {
            return Boolean.FALSE;
        }
    }
}
