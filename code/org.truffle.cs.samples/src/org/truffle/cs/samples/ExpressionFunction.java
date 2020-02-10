package org.truffle.cs.samples;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class ExpressionFunction extends RootNode {
    @Child Expression body;
    final String name;

    public ExpressionFunction(Expression body, String name) {
        super(null);
        this.body = body;
        this.name = name;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return body.execute((int[]) frame.getArguments()[0]);
    }

    @Override
    public String toString() {
        return name;
    }
}