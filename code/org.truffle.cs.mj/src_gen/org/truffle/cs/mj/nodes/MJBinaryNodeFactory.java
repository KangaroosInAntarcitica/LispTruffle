// CheckStyle: start generated
package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import org.truffle.cs.mj.nodes.MJBinaryNode;
import org.truffle.cs.mj.nodes.MJExpressionNode;
import org.truffle.cs.mj.nodes.MJBinaryNode.AddNode;
import org.truffle.cs.mj.nodes.MJBinaryNode.DivNode;
import org.truffle.cs.mj.nodes.MJBinaryNode.MulNode;
import org.truffle.cs.mj.nodes.MJBinaryNode.SubNode;

@GeneratedBy(MJBinaryNode.class)
public final class MJBinaryNodeFactory {

    @GeneratedBy(AddNode.class)
    public static final class AddNodeGen extends AddNode {

        @Child private MJExpressionNode x_;
        @Child private MJExpressionNode y_;
        @CompilationFinal private int state_;

        private AddNodeGen(MJExpressionNode x, MJExpressionNode y) {
            this.x_ = x;
            this.y_ = y;
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            int state = state_;
            int xValue_ = this.x_.executeI32(frameValue);
            int yValue_ = this.y_.executeI32(frameValue);
            if (state != 0 /* is-active add(int, int) */) {
                return add(xValue_, yValue_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(xValue_, yValue_);
        }

        @Override
        public int executeI32(VirtualFrame frameValue) {
            int state = state_;
            int xValue_ = this.x_.executeI32(frameValue);
            int yValue_ = this.y_.executeI32(frameValue);
            if (state != 0 /* is-active add(int, int) */) {
                return add(xValue_, yValue_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(xValue_, yValue_);
        }

        private int executeAndSpecialize(Object xValue, Object yValue) {
            int state = state_;
            if (xValue instanceof Integer) {
                int xValue_ = (int) xValue;
                if (yValue instanceof Integer) {
                    int yValue_ = (int) yValue;
                    this.state_ = state = state | 0b1 /* add-active add(int, int) */;
                    return add(xValue_, yValue_);
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[] {this.x_, this.y_}, xValue, yValue);
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

        public static AddNode create(MJExpressionNode x, MJExpressionNode y) {
            return new AddNodeGen(x, y);
        }

    }
    @GeneratedBy(SubNode.class)
    public static final class SubNodeGen extends SubNode {

        @Child private MJExpressionNode x_;
        @Child private MJExpressionNode y_;
        @CompilationFinal private int state_;

        private SubNodeGen(MJExpressionNode x, MJExpressionNode y) {
            this.x_ = x;
            this.y_ = y;
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            int state = state_;
            int xValue_ = this.x_.executeI32(frameValue);
            int yValue_ = this.y_.executeI32(frameValue);
            if (state != 0 /* is-active sub(int, int) */) {
                return sub(xValue_, yValue_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(xValue_, yValue_);
        }

        @Override
        public int executeI32(VirtualFrame frameValue) {
            int state = state_;
            int xValue_ = this.x_.executeI32(frameValue);
            int yValue_ = this.y_.executeI32(frameValue);
            if (state != 0 /* is-active sub(int, int) */) {
                return sub(xValue_, yValue_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(xValue_, yValue_);
        }

        private int executeAndSpecialize(Object xValue, Object yValue) {
            int state = state_;
            if (xValue instanceof Integer) {
                int xValue_ = (int) xValue;
                if (yValue instanceof Integer) {
                    int yValue_ = (int) yValue;
                    this.state_ = state = state | 0b1 /* add-active sub(int, int) */;
                    return sub(xValue_, yValue_);
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[] {this.x_, this.y_}, xValue, yValue);
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

        public static SubNode create(MJExpressionNode x, MJExpressionNode y) {
            return new SubNodeGen(x, y);
        }

    }
    @GeneratedBy(MulNode.class)
    public static final class MulNodeGen extends MulNode {

        @Child private MJExpressionNode x_;
        @Child private MJExpressionNode y_;
        @CompilationFinal private int state_;

        private MulNodeGen(MJExpressionNode x, MJExpressionNode y) {
            this.x_ = x;
            this.y_ = y;
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            int state = state_;
            int xValue_ = this.x_.executeI32(frameValue);
            int yValue_ = this.y_.executeI32(frameValue);
            if (state != 0 /* is-active mul(int, int) */) {
                return mul(xValue_, yValue_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(xValue_, yValue_);
        }

        @Override
        public int executeI32(VirtualFrame frameValue) {
            int state = state_;
            int xValue_ = this.x_.executeI32(frameValue);
            int yValue_ = this.y_.executeI32(frameValue);
            if (state != 0 /* is-active mul(int, int) */) {
                return mul(xValue_, yValue_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(xValue_, yValue_);
        }

        private int executeAndSpecialize(Object xValue, Object yValue) {
            int state = state_;
            if (xValue instanceof Integer) {
                int xValue_ = (int) xValue;
                if (yValue instanceof Integer) {
                    int yValue_ = (int) yValue;
                    this.state_ = state = state | 0b1 /* add-active mul(int, int) */;
                    return mul(xValue_, yValue_);
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[] {this.x_, this.y_}, xValue, yValue);
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

        public static MulNode create(MJExpressionNode x, MJExpressionNode y) {
            return new MulNodeGen(x, y);
        }

    }
    @GeneratedBy(DivNode.class)
    public static final class DivNodeGen extends DivNode {

        @Child private MJExpressionNode x_;
        @Child private MJExpressionNode y_;
        @CompilationFinal private int state_;

        private DivNodeGen(MJExpressionNode x, MJExpressionNode y) {
            this.x_ = x;
            this.y_ = y;
        }

        @Override
        public Object executeGeneric(VirtualFrame frameValue) {
            int state = state_;
            int xValue_ = this.x_.executeI32(frameValue);
            int yValue_ = this.y_.executeI32(frameValue);
            if (state != 0 /* is-active div(int, int) */) {
                return div(xValue_, yValue_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(xValue_, yValue_);
        }

        @Override
        public int executeI32(VirtualFrame frameValue) {
            int state = state_;
            int xValue_ = this.x_.executeI32(frameValue);
            int yValue_ = this.y_.executeI32(frameValue);
            if (state != 0 /* is-active div(int, int) */) {
                return div(xValue_, yValue_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return executeAndSpecialize(xValue_, yValue_);
        }

        private int executeAndSpecialize(Object xValue, Object yValue) {
            int state = state_;
            if (xValue instanceof Integer) {
                int xValue_ = (int) xValue;
                if (yValue instanceof Integer) {
                    int yValue_ = (int) yValue;
                    this.state_ = state = state | 0b1 /* add-active div(int, int) */;
                    return div(xValue_, yValue_);
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[] {this.x_, this.y_}, xValue, yValue);
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

        public static DivNode create(MJExpressionNode x, MJExpressionNode y) {
            return new DivNodeGen(x, y);
        }

    }
}
