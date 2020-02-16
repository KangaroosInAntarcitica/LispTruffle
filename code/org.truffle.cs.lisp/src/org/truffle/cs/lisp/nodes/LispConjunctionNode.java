package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeInfo
public abstract class LispConjunctionNode extends LispOperationNode {

    public static class NOT extends LispConjunctionNode {
        @Override
        public void setArguments(LispExpressionNode arguments[]) {
            if (arguments.length != 1) {
                CompilerDirectives.transferToInterpreter();
                throw new Error("Not must be used with one argument");
            }
            this.arguments = arguments;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return ! ((boolean) arguments[0].execute(frame));
        }
    }

    public static class AND extends LispConjunctionNode {
        @Override
        @ExplodeLoop()
        public Object execute(VirtualFrame frame) {
            for (LispExpressionNode arg : arguments) {
                if ((boolean) arg.execute(frame) == Boolean.FALSE) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }
    }

    public static class OR extends LispConjunctionNode {
        @Override
        @ExplodeLoop()
        public Object execute(VirtualFrame frame) {
            for (LispExpressionNode arg : arguments) {
                if ((boolean) arg.execute(frame) == Boolean.TRUE) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }
    }
}
