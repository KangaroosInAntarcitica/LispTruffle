package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild(value="x", type=MJExpressionNode.class)
@NodeChild(value="y", type=MJExpressionNode.class)
public abstract class MJBinaryNode extends MJExpressionNode {
    public static abstract class AddNode extends MJBinaryNode {
        @Specialization
        public int add(int x, int y) {
            return x + y;
        }
    }

    public static abstract class SubNode extends MJBinaryNode {
        @Specialization
        public int sub(int x, int y) {
            return x - y;
        }
    }

    public static abstract class MulNode extends MJBinaryNode {
        @Specialization
        public int mul(int x, int y) {
            return x * y;
        }
    }

    public static abstract class DivNode extends MJBinaryNode {
        @Specialization
        public int div(int x, int y) {
            return x / y;
        }
    }
}
