package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import org.truffle.cs.lisp.types.LispCallable;

@NodeInfo(language="Lisp Truffle", description="Lisp Truffle language node")
public abstract class LispExpressionNode extends Node {
    public abstract Object execute(VirtualFrame frame);

    public LispCallable executeLispCallable(VirtualFrame frame) {
        Object object = execute(frame);
        if (object instanceof LispCallable) {
            return (LispCallable) object;
        } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new RuntimeException("Wrong type: should have been Lisp Callable");
        }
    };
}
