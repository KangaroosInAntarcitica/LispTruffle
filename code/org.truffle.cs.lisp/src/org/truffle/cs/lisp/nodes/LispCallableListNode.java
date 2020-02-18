package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import org.truffle.cs.lisp.types.LispCallable;

public class LispCallableListNode extends LispExpressionNode {
	@Child LispExpressionNode operation;
	@Children LispExpressionNode[] arguments;
	@Child IndirectCallNode callNode;
	
	public LispCallableListNode(LispExpressionNode operation, LispExpressionNode[] arguments) {
		this.operation = operation;
		this.arguments = arguments;
		this.callNode = Truffle.getRuntime().createIndirectCallNode();
	}
	
	@Override
    @ExplodeLoop
	public Object execute(VirtualFrame frame) {
		LispCallable callable = operation.executeLispCallable(frame);

		Object[] argumentValues = new Object[arguments.length + 1];
		argumentValues[0] = callable.getFrame();
		for (int i = 0; i < arguments.length; i++) {
			argumentValues[i + 1] = arguments[i].execute(frame);
		}

		return callNode.call(callable.getCallTarget(), argumentValues);
	}
}
