package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import org.truffle.cs.lisp.types.LispCallable;

public abstract class LispCallableNode extends LispExpressionNode {
    public final Object execute(VirtualFrame frame) {
        return executeOperation(frame);
    }

    public abstract LispCallable executeOperation(VirtualFrame frame);

}
