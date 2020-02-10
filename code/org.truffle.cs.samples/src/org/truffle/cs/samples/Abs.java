package org.truffle.cs.samples;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;

public class Abs extends Expression {
    @Child Expression operand;

    public Abs(Expression operand) {
        this.operand = operand;
    }

    int executeSimple(int[] arguments) {
        int value = operand.execute(arguments);
        if (value >= 0) {
            return 0;
        } else {
            return -value;
        }
    }

    @CompilationFinal boolean seenNegative;

    @Override
    int execute(int[] arguments) {
        int value = operand.execute(arguments);
        if (value >= 0) {
            return value;
        } else {
            if (!seenNegative) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                seenNegative = true;
            }
            return -value;
        }
    }

}
