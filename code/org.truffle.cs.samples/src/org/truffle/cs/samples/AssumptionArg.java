package org.truffle.cs.samples;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.Truffle;

public class AssumptionArg extends Expression {
    @Child Expression child;

    final Assumption a = Truffle.getRuntime().createAssumption();

    public AssumptionArg(Expression child) {
        this.child = child;
    }

    @Override
    int execute(int[] arguments) {
        if (a.isValid()) {
            return 10;
        }
        return child.execute(arguments);
    }

}
