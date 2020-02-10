package org.truffle.cs.samples;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleRuntime;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

public class SimplestPEA {

    public static abstract class ExpressionNode extends Node {
        public abstract Object execute(VirtualFrame frame);
    }

    public static abstract class ConstantNode extends ExpressionNode {
        private final int constant;

        public ConstantNode(int constant) {
            this.constant = constant;
        }

        @Specialization
        public int c() {
            return constant;
        }
    }

    @NodeChild(value = "op1", type = ExpressionNode.class)
    @NodeChild(value = "op2", type = ExpressionNode.class)
    public static abstract class BinaryExpression extends ExpressionNode {
    }

    public static abstract class AddNode extends BinaryExpression {
        @Specialization
        public int add(int a, int b) {
            return a + b;
        }

        @Specialization
        public int add(int a, long b) {
            return a + (int) b;
        }

        @Specialization
        public float add(float a, float b) {
            return a + b;
        }

        @Specialization
        public float add(Object o1, Object o2) {
            throw new UnsupportedOperationException();
        }
    }

    @NodeChild(value = "operand", type = ExpressionNode.class)
    public static abstract class UnaryExpression extends ExpressionNode {
    }

    public abstract static class NegateNode extends UnaryExpression {

        @Specialization
        public int negate(int op) {
            return -op;
        }
    }

    public abstract static class PrintNode extends UnaryExpression {

        @Specialization
        int p(int i) {
            print(i);
            return i;
        }

        @TruffleBoundary
        static void print(int i) {
            System.out.println(i);
        }
    }

    public static class ExpressionAST extends RootNode {
        @Child ExpressionNode expr;

        protected ExpressionAST(ExpressionNode expr) {
            super(null);
            this.expr = expr;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return expr.execute(null);
        }

    }

    public static void main(String[] args) {
        ConstantNode c = SimplestPEAFactory.ConstantNodeGen.create(42);
        NegateNode negate = SimplestPEAFactory.NegateNodeGen.create(c);
        AddNode add = SimplestPEAFactory.AddNodeGen.create(negate, negate);
        PrintNode print = SimplestPEAFactory.PrintNodeGen.create(add);
        TruffleRuntime rt = Truffle.getRuntime();
        CallTarget t = rt.createCallTarget(new ExpressionAST(print));
        t.call();
        t.call();
    }
}
