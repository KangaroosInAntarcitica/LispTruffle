package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import org.truffle.cs.lisp.types.LispList;

public class IfNode extends LispExpressionNode {
    @Child LispExpressionNode predicateNode;
    @Child LispExpressionNode trueNode;
    @Child LispExpressionNode falseNode;

    public IfNode(LispExpressionNode predicateNode, LispExpressionNode trueNode, LispExpressionNode falseNode) {
        this.predicateNode = predicateNode;
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object result = predicateNode.execute(frame);
        boolean isTrue;
        if (result instanceof Integer) {
            isTrue = (Integer) result != 0;
        } else if (result instanceof LispList) {
            isTrue = !((LispList) result).empty();
        } else {
            isTrue = result != null;
        }

        if (isTrue) {
            return trueNode.execute(frame);
        } else {
            return falseNode.execute(frame);
        }
    }
}
