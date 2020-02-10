package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;

public class MJBlock extends MJStatementNode {

    @Children MJStatementNode statements[];

    public MJBlock(MJStatementNode statements[]) {
        this.statements = statements;
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frame) {
        for (MJStatementNode statement: statements) {
            statement.execute(frame);
        }
        return null;
    }
}
