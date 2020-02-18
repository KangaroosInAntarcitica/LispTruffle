package org.truffle.cs.lisp.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import org.truffle.cs.lisp.types.LispList;

public class LispPrintNode extends LispExpressionNode {
    @Override
    public Object execute(VirtualFrame frame) {
        print(frame.getArguments()[1]);
        if (frame.getArguments().length > 2) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new RuntimeException("Print supports only 1 argument");
        }
        return frame.getArguments()[1];
    }

    public void print(LispList list) {
        print("(");
        while (!list.empty()) {
            print(list.getValue());
            list = list.getRest();
            if (!list.empty()) print(", ");
        }
        print(")");
    }

    @CompilerDirectives.TruffleBoundary
    public void print(int value) {
        System.out.println(value);
    }

    @CompilerDirectives.TruffleBoundary
    public void print(String value) {
        System.out.println(value);
    }

    public void print(Object value) {
        if (value instanceof Integer) print((int) value);
        else if (value instanceof LispList) print((LispList) value);
        else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new RuntimeException("Cannot print the object");
        }
    }
}
