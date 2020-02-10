// CheckStyle: start generated
package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import org.truffle.cs.mj.nodes.MJExpressionNode;
import org.truffle.cs.mj.nodes.MJVariableNode;
import org.truffle.cs.mj.nodes.MJVariableNode.MJReadLocalVariableNode;
import org.truffle.cs.mj.nodes.MJVariableNode.MJWriteLocalVariableNode;

@GeneratedBy(MJVariableNode.class)
public final class MJVariableNodeFactory {

    @GeneratedBy(MJReadLocalVariableNode.class)
    public static final class MJReadLocalVariableNodeGen extends MJReadLocalVariableNode {

        private final FrameSlot slot;

        private MJReadLocalVariableNodeGen(FrameSlot slot) {
            this.slot = slot;
        }

        @Override
        protected FrameSlot getSlot() {
            return this.slot;
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            return readVariable(frameValue);
        }

        @Override
        public int executeI32(VirtualFrame frameValue) {
            return (int) executeGeneric(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        public static MJReadLocalVariableNode create(FrameSlot slot) {
            return new MJReadLocalVariableNodeGen(slot);
        }

    }
    @GeneratedBy(MJWriteLocalVariableNode.class)
    public static final class MJWriteLocalVariableNodeGen extends MJWriteLocalVariableNode {

        private final FrameSlot slot;
        @Child private MJExpressionNode value_;

        private MJWriteLocalVariableNodeGen(MJExpressionNode value, FrameSlot slot) {
            this.slot = slot;
            this.value_ = value;
        }

        @Override
        protected FrameSlot getSlot() {
            return this.slot;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object valueValue_ = this.value_.executeGeneric(frameValue);
            return execute(frameValue, valueValue_);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        public static MJWriteLocalVariableNode create(MJExpressionNode value, FrameSlot slot) {
            return new MJWriteLocalVariableNodeGen(value, slot);
        }

    }
}
