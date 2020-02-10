package org.truffle.cs.samples;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleRuntime;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

public class LiveCoding {

    @TruffleBoundary
    static void foo() {
        System.out.println("Hello World");
    }

    static class FunctionNode extends RootNode {

        protected FunctionNode() {
            super(null);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            foo();
            return null;
        }

        @Override
        public String toString() {
            return "TestRootNode";
        }
    }

    static void helloWorld() {
        TruffleRuntime runtime = Truffle.getRuntime();
        CallTarget ct = runtime.createCallTarget(new FunctionNode());
        ct.call();
        ct.call();
    }

    public static void main(String[] args) {
        // helloWorld();
        // expressionExample();
        System.out.println("morning...");
    }

    static void expressionExample() {
        // ExpressionFunction expressionSample = new ExpressionFunction(new Add(new Add(new Arg(0),
        // new Arg(1)), new Arg(2)), "simpleAddExpression");
        // callAndCompile(expressionSample,new int[]{10, 11, 21});

        // ExpressionFunction absSample = new ExpressionFunction(new Abs(new Arg(0)), "simplArg");
        // callAndCompile(absSample, new int[]{10});
        // callAndCompile(absSample, new int[]{-11});
        // callAndCompile(absSample, new int[]{11});

        AssumptionArg arg = new AssumptionArg(new Arg(0));
        ExpressionFunction absSample = new ExpressionFunction(arg, "simplestAssumption");
        callAndCompile(absSample, new int[]{10});
        arg.a.invalidate();
        callAndCompile(absSample, new int[]{10});
    }

    static void callAndCompile(ExpressionFunction ex, int[] args) {
        TruffleRuntime runtime = Truffle.getRuntime();
        CallTarget ct = runtime.createCallTarget(ex);
        System.out.println(ct.call(args));
        System.out.println(ct.call(args));
    }
}
