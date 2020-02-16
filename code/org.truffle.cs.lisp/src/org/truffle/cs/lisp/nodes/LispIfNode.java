package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;

public class LispIfNode extends LispOperationNode {
    @Child private LispExpressionNode condition;
    @Child private LispExpressionNode trueBranch;
    @Child private LispExpressionNode falseBranch;
	
	public LispIfNode() {}

    @Override
	public void setArguments(LispExpressionNode arguments[]) {
        // TODO after adding nil type, add if option without falseBranch
        if (arguments.length != 3) {
            throw new Error("Invalid if expression");
        }

        condition = arguments[0];
        trueBranch = arguments[1];
        falseBranch = arguments[2];
    }

    @Override
    public Object execute(VirtualFrame frame) {
        if ((boolean) condition.execute(frame)) {
            return trueBranch.execute(frame);
        } else {
            return falseBranch.execute(frame);
        }
    }
}
