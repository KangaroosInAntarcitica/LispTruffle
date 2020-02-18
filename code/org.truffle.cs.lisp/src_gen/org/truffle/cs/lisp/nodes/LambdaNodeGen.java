// CheckStyle: start generated
package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import org.truffle.cs.lisp.nodes.LambdaNode;
import org.truffle.cs.lisp.types.LispCallable;

@GeneratedBy(LambdaNode.class)
public final class LambdaNodeGen extends LambdaNode {

    private final LispCallable callable;

    private LambdaNodeGen(LispCallable callable) {
        this.callable = callable;
    }

    @Override
    public LispCallable getCallable() {
        return this.callable;
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        return getCallableObject(frameValue);
    }

    @Override
    public LispCallable executeLispCallable(VirtualFrame frameValue) {
        return (LispCallable) execute(frameValue);
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    public static LambdaNode create(LispCallable callable) {
        return new LambdaNodeGen(callable);
    }

}
