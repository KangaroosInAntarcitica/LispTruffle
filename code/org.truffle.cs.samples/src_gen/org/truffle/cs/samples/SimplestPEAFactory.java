// CheckStyle: start generated
package org.truffle.cs.samples;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import org.truffle.cs.samples.SimplestPEA;
import org.truffle.cs.samples.SimplestPEA.AddNode;
import org.truffle.cs.samples.SimplestPEA.ConstantNode;
import org.truffle.cs.samples.SimplestPEA.ExpressionNode;
import org.truffle.cs.samples.SimplestPEA.NegateNode;
import org.truffle.cs.samples.SimplestPEA.PrintNode;

@GeneratedBy(SimplestPEA.class)
public final class SimplestPEAFactory {

    @GeneratedBy(ConstantNode.class)
    public static final class ConstantNodeGen extends ConstantNode {

        private ConstantNodeGen(int constant) {
            super(constant);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return c();
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        public static ConstantNode create(int constant) {
            return new ConstantNodeGen(constant);
        }

    }
    @GeneratedBy(AddNode.class)
    public static final class AddNodeGen extends AddNode {

        @Child private ExpressionNode op1_;
        @Child private ExpressionNode op2_;
        @CompilationFinal private int state_;

        private AddNodeGen(ExpressionNode op1, ExpressionNode op2) {
            this.op1_ = op1;
            this.op2_ = op2;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state = state_;
            Object op1Value_ = this.op1_.execute(frameValue);
            Object op2Value_ = this.op2_.execute(frameValue);
            if ((state & 0b11) != 0 /* is-active add(int, int) || add(int, long) */ && op1Value_ instanceof Integer) {
                int op1Value__ = (int) op1Value_;
                if ((state & 0b1) != 0 /* is-active add(int, int) */ && op2Value_ instanceof Integer) {
                    int op2Value__ = (int) op2Value_;
                    return add(op1Value__, op2Value__);
                }
                if ((state & 0b10) != 0 /* is-active add(int, long) */ && op2Value_ instanceof Long) {
                    long op2Value__ = (long) op2Value_;
                    return add(op1Value__, op2Value__);
                }
            }
            if ((state & 0b100) != 0 /* is-active add(float, float) */ && op1Value_ instanceof Float) {
                float op1Value__ = (float) op1Value_;
                if (op2Value_ instanceof Float) {
                    float op2Value__ = (float) op2Value_;
                    return add(op1Value__, op2Value__);
                }
            }
            if ((state & 0b1000) != 0 /* is-active add(Object, Object) */) {
                return add(op1Value_, op2Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(op1Value_, op2Value_);
        }

        private Object executeAndSpecialize(Object op1Value, Object op2Value) {
            int state = state_;
            if (op1Value instanceof Integer) {
                int op1Value_ = (int) op1Value;
                if (op2Value instanceof Integer) {
                    int op2Value_ = (int) op2Value;
                    this.state_ = state = state | 0b1 /* add-active add(int, int) */;
                    return add(op1Value_, op2Value_);
                }
                if (op2Value instanceof Long) {
                    long op2Value_ = (long) op2Value;
                    this.state_ = state = state | 0b10 /* add-active add(int, long) */;
                    return add(op1Value_, op2Value_);
                }
            }
            if (op1Value instanceof Float) {
                float op1Value_ = (float) op1Value;
                if (op2Value instanceof Float) {
                    float op2Value_ = (float) op2Value;
                    this.state_ = state = state | 0b100 /* add-active add(float, float) */;
                    return add(op1Value_, op2Value_);
                }
            }
            this.state_ = state = state | 0b1000 /* add-active add(Object, Object) */;
            return add(op1Value, op2Value);
        }

        @Override
        public NodeCost getCost() {
            int state = state_;
            if (state == 0b0) {
                return NodeCost.UNINITIALIZED;
            } else if ((state & (state - 1)) == 0 /* is-single-active  */) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static AddNode create(ExpressionNode op1, ExpressionNode op2) {
            return new AddNodeGen(op1, op2);
        }

    }
    @GeneratedBy(NegateNode.class)
    public static final class NegateNodeGen extends NegateNode {

        @Child private ExpressionNode operand_;
        @CompilationFinal private int state_;

        private NegateNodeGen(ExpressionNode operand) {
            this.operand_ = operand;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state = state_;
            Object operandValue_ = this.operand_.execute(frameValue);
            if (state != 0 /* is-active negate(int) */ && operandValue_ instanceof Integer) {
                int operandValue__ = (int) operandValue_;
                return negate(operandValue__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(operandValue_);
        }

        private int executeAndSpecialize(Object operandValue) {
            int state = state_;
            if (operandValue instanceof Integer) {
                int operandValue_ = (int) operandValue;
                this.state_ = state = state | 0b1 /* add-active negate(int) */;
                return negate(operandValue_);
            }
            throw new UnsupportedSpecializationException(this, new Node[] {this.operand_}, operandValue);
        }

        @Override
        public NodeCost getCost() {
            int state = state_;
            if (state == 0b0) {
                return NodeCost.UNINITIALIZED;
            } else {
                return NodeCost.MONOMORPHIC;
            }
        }

        public static NegateNode create(ExpressionNode operand) {
            return new NegateNodeGen(operand);
        }

    }
    @GeneratedBy(PrintNode.class)
    public static final class PrintNodeGen extends PrintNode {

        @Child private ExpressionNode operand_;
        @CompilationFinal private int state_;

        private PrintNodeGen(ExpressionNode operand) {
            this.operand_ = operand;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state = state_;
            Object operandValue_ = this.operand_.execute(frameValue);
            if (state != 0 /* is-active p(int) */ && operandValue_ instanceof Integer) {
                int operandValue__ = (int) operandValue_;
                return p(operandValue__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(operandValue_);
        }

        private int executeAndSpecialize(Object operandValue) {
            int state = state_;
            if (operandValue instanceof Integer) {
                int operandValue_ = (int) operandValue;
                this.state_ = state = state | 0b1 /* add-active p(int) */;
                return p(operandValue_);
            }
            throw new UnsupportedSpecializationException(this, new Node[] {this.operand_}, operandValue);
        }

        @Override
        public NodeCost getCost() {
            int state = state_;
            if (state == 0b0) {
                return NodeCost.UNINITIALIZED;
            } else {
                return NodeCost.MONOMORPHIC;
            }
        }

        public static PrintNode create(ExpressionNode operand) {
            return new PrintNodeGen(operand);
        }

    }
}
