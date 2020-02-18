package org.truffle.cs.lisp.main;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleRuntime;
import com.oracle.truffle.api.nodes.DirectCallNode;
import org.truffle.cs.lisp.parser.RecursiveDescentParser;
import org.truffle.cs.lisp.parser.RecursiveDescentScanner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LispRuntime {
    public static void main(String[] args) {
        parseRD(ScopedProgram);
    }

    static String ArithmeticProgram =
            "(+ 1 0 (- 2 3))" +
            "(define a 2)" +
            "(* a 2 (- 2 3))"; // -4
    static String lambdaProgram =
            "(define fun (lambda b (* b 2)))" +
            "(println (fun 4))"; // 8
    static String ScopedProgram =
            "(define fun (lambda (b c) (lambda a (* a (+ b c)))))" +
            "(define inner (fun 0 2))" +
            "(inner 3)"; // 6

    static void parseRD(String code) {
        InputStream is = new ByteArrayInputStream(code.getBytes());
        RecursiveDescentScanner scanner = new RecursiveDescentScanner(new InputStreamReader(is));
        RecursiveDescentParser parser = new RecursiveDescentParser(scanner);
        parser.parse();

        TruffleRuntime runtime = Truffle.getRuntime();
        System.out.println("[Parser: Parsed successfully & Calling]");
        CallTarget callTarget = runtime.createCallTarget(parser.getRootNode());
        DirectCallNode callNode = Truffle.getRuntime().createDirectCallNode(callTarget);
        
        for (int i = 0; i < 1000; i++) {
        	callNode.call(new Object[]{null});
        }

        Object result = callNode.call(new Object[] {null});
        System.out.println(result);
    }

//    static VirtualFrame createFrame(FrameDescriptor descriptor) {
//        VirtualFrame frame = Truffle.getRuntime().createVirtualFrame(new Object[]{null}, descriptor);
//        frame.setObject(descriptor.findOrAddFrameSlot("+"),
//                LispCallable.createLispCallable(new LispArithmeticNode.LispAddNode(), descriptor));
//        frame.setObject(descriptor.findOrAddFrameSlot("-"),
//                LispCallable.createLispCallable(new LispArithmeticNode.LispSubtractNode(), descriptor));
//        frame.setObject(descriptor.findOrAddFrameSlot("*"),
//                LispCallable.createLispCallable(new LispArithmeticNode.LispMultiplyNode(), descriptor));
//        frame.setObject(descriptor.findOrAddFrameSlot("/"),
//                LispCallable.createLispCallable(new LispArithmeticNode.LispDivideNode(), descriptor));
//        return frame;
//    }
}
