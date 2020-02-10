package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;


public class MJVariableNode {

    @NodeField(name="slot", type=FrameSlot.class)
    public abstract static class MJReadLocalVariableNode extends MJExpressionNode {
        protected abstract FrameSlot getSlot();

        @Specialization
        public Object readVariable(VirtualFrame frame) {
            try {
                frame.getObject(getSlot());
            } catch(FrameSlotTypeException e) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new Error(e);
            }
            return null;
        }
    }

    @NodeChild(value="value", type=MJExpressionNode.class)
    @NodeField(name="slot", type= FrameSlot.class)
    public abstract static class MJWriteLocalVariableNode extends MJStatementNode {
        protected abstract FrameSlot getSlot();

        @Specialization
        public Object execute(VirtualFrame frame, Object value) {
            frame.setObject(getSlot(), value);
            return null;
        }
    }
}
