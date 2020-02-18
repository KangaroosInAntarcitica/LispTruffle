package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import org.truffle.cs.lisp.types.LispCallable;

@NodeField(name="callable", type=LispCallable.class)
public abstract class LambdaNode extends LispExpressionNode {
    public abstract LispCallable getCallable();

    @Specialization
    public Object getCallableObject(VirtualFrame frame) {
        return createCallable(frame);
    }

    private LispCallable createCallable(VirtualFrame frame) {
        LispCallable callable = getCallable();
        callable.setFrame(frame.materialize());
        return callable;
    }
}
