package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import org.truffle.cs.lisp.types.LispCallable;

@NodeInfo(language="Lisp Truffle")
public class LispMainNode extends RootNode {
    // Children to execute
    @Children LispExpressionNode[] body;
    // Built-in global variables to add
    FrameSlot[] builtInFrameSlots;
    LispCallable[] builtInCallables;

    public LispMainNode(LispExpressionNode[] body, FrameDescriptor descriptor, FrameSlot[] builtInFrameSlots, LispCallable[] builtInCallables) {
        super(null, descriptor);
        this.body = body;
        this.builtInFrameSlots = builtInFrameSlots;
        this.builtInCallables = builtInCallables;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        addBuiltInCallables(frame);
        return callChildren(frame);
    }

    private void addBuiltInCallables(VirtualFrame frame) {
        for (int i = 0; i < builtInFrameSlots.length; i++) {
            frame.setObject(builtInFrameSlots[i], builtInCallables[i]);
        }
    }

    @ExplodeLoop
    private Object callChildren(VirtualFrame frame) {
        Object result = null;
        for (LispExpressionNode expression: body) {
            result = expression.execute(frame);
        }
        return result;
    }
}