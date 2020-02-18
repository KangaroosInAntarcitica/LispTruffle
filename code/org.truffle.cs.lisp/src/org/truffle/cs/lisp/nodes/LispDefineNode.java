package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LispDefineNode extends LispExpressionNode {
    FrameSlot slot;
    @Child LispExpressionNode value;

    public LispDefineNode(FrameSlot slot, LispExpressionNode node) {
        this.slot = slot;
        this.value = node;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object result = value.execute(frame);
        frame.setObject(slot, result);
        return result;
    }
}
