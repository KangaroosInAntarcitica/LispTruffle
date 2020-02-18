package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

@NodeField(name="slot", type=FrameSlot.class)
@NodeField(name="depth", type=int.class)
public abstract class LispVariableNode extends LispExpressionNode {
    public abstract FrameSlot getSlot();
    public abstract int getDepth();

    @Specialization
    @ExplodeLoop
    public Object objectVariable(VirtualFrame virtualFrame) {
        Frame frame = virtualFrame;

        for (int i = 0; i < getDepth(); i++) {
            frame = (Frame) frame.getArguments()[0];
        }

        return frame.getValue(getSlot());
    }
}
