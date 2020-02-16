package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.frame.VirtualFrame;

@NodeInfo
public abstract class LispComparisonNode extends LispOperationNode {

    public static class Equal extends LispComparisonNode {
        @Override
        @ExplodeLoop()
        public Object execute(VirtualFrame frame) {
            float compared = (float) arguments[0].execute(frame);
            for (int i = 1; i < arguments.length; i++) {
                if (compared != (float) arguments[i].execute(frame)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }
    }

    public static class Greater extends LispComparisonNode {
        @Override
        @ExplodeLoop()
        public Object execute(VirtualFrame frame) {
            float compared = (float) arguments[0].execute(frame);
            for (int i = 1; i < arguments.length; i++) {
                if (compared <= (float) arguments[i].execute(frame)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }
    }

    public static class GreaterEqual extends LispComparisonNode {
        @Override
        @ExplodeLoop()
        public Object execute(VirtualFrame frame) {
            float compared = (float) arguments[0].execute(frame);
            for (int i = 1; i < arguments.length; i++) {
                if (compared < (float) arguments[i].execute(frame)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }
    }

    public static class Less extends LispComparisonNode {
        @Override
        @ExplodeLoop()
        public Object execute(VirtualFrame frame) {
            float compared = (float) arguments[0].execute(frame);
            for (int i = 1; i < arguments.length; i++) {
                if (compared >= (float) arguments[i].execute(frame)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }
    }

    public static class LessEqual extends LispComparisonNode {
        @Override
        @ExplodeLoop()
        public Object execute(VirtualFrame frame) {
            float compared = (float) arguments[0].execute(frame);
            for (int i = 1; i < arguments.length; i++) {
                if (compared > (float) arguments[i].execute(frame)) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }
    }
}
