// CheckStyle: start generated
package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import org.truffle.cs.lisp.nodes.LispVariableNode;
import org.truffle.cs.lisp.types.LispCallable;

@GeneratedBy(LispVariableNode.class)
public final class LispVariableNodeGen extends LispVariableNode {

    private final FrameSlot slot;
    private final int depth;

    private LispVariableNodeGen(FrameSlot slot, int depth) {
        this.slot = slot;
        this.depth = depth;
    }

    @Override
    public FrameSlot getSlot() {
        return this.slot;
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        return objectVariable(frameValue);
    }

    @Override
    public LispCallable executeLispCallable(VirtualFrame frameValue) {
        return (LispCallable) execute(frameValue);
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    public static LispVariableNode create(FrameSlot slot, int depth) {
        return new LispVariableNodeGen(slot, depth);
    }

}
