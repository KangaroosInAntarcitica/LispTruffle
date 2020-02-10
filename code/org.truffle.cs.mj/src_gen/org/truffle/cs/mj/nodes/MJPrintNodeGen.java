// CheckStyle: start generated
package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import org.truffle.cs.mj.nodes.MJExpressionNode;
import org.truffle.cs.mj.nodes.MJPrintNode;

@GeneratedBy(MJPrintNode.class)
public final class MJPrintNodeGen extends MJPrintNode {

    @Child private MJExpressionNode expression_;
    @CompilationFinal private int state_;

    private MJPrintNodeGen(MJExpressionNode expression) {
        this.expression_ = expression;
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b10) == 0 /* only-active printI(int) */ && state != 0  /* is-not printI(int) && printO(Object) */) {
            return execute_int0(frameValue, state);
        } else {
            return execute_generic1(frameValue, state);
        }
    }

    private Object execute_int0(VirtualFrame frameValue, int state) {
        int expressionValue_ = this.expression_.executeI32(frameValue);
        assert (state & 0b1) != 0 /* is-active printI(int) */;
        return printI(expressionValue_);
    }

    private Object execute_generic1(VirtualFrame frameValue, int state) {
        Object expressionValue_ = this.expression_.executeGeneric(frameValue);
        if ((state & 0b1) != 0 /* is-active printI(int) */ && expressionValue_ instanceof Integer) {
            int expressionValue__ = (int) expressionValue_;
            return printI(expressionValue__);
        }
        if ((state & 0b10) != 0 /* is-active printO(Object) */) {
            return printO(expressionValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(expressionValue_);
    }

    private Object executeAndSpecialize(Object expressionValue) {
        int state = state_;
        if (expressionValue instanceof Integer) {
            int expressionValue_ = (int) expressionValue;
            this.state_ = state = state | 0b1 /* add-active printI(int) */;
            return printI(expressionValue_);
        }
        this.state_ = state = state | 0b10 /* add-active printO(Object) */;
        return printO(expressionValue);
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

    public static MJPrintNode create(MJExpressionNode expression) {
        return new MJPrintNodeGen(expression);
    }

}
