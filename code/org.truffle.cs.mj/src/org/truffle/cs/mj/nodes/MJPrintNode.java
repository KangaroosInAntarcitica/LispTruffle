package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeChild(value="expression", type=MJExpressionNode.class)
public abstract class MJPrintNode extends MJStatementNode {

    @Specialization
    Object printI(int i) {
        return null;
    }

    @Specialization
    Object printO(Object o) {
        return null;
    }

    @TruffleBoundary
    public static void print(Object o) {
        System.out.println(o);
    }

    @TruffleBoundary
    public static void print(int i) {
        System.out.println(i);
    }

}
