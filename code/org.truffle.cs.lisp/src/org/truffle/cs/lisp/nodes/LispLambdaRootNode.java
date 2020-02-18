package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LispLambdaRootNode extends LispRootNode {
    FrameSlot[] argumentSlots;

    public LispLambdaRootNode(LispExpressionNode body, FrameDescriptor frameDescriptor, FrameSlot[] argumentSlots) {
        super(body, frameDescriptor);
        this.argumentSlots = argumentSlots;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        setArguments(frame);
        return super.execute(frame);
    }

    // @ExplodeLoop
    private void setArguments(VirtualFrame frame) {
        for (int i = 0; i < argumentSlots.length; i++) {
            frame.setObject(argumentSlots[i], frame.getArguments()[i + 1]);
        }
    }
}
