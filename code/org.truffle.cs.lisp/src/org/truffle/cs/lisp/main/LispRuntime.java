package org.truffle.cs.lisp.main;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.truffle.cs.lisp.parser.RecursiveDescentScanner;
import org.truffle.cs.lisp.parser.RecursiveDescentParser;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleRuntime;

public class LispRuntime {
    public static void main(String[] args) {
        parseRD(SimpleProgram);
    }

    static String SimpleProgram = "(+ 1 1 2)";

    static void parseRD(String code) {
        InputStream is = new ByteArrayInputStream(code.getBytes());
        RecursiveDescentScanner scanner = new RecursiveDescentScanner(new InputStreamReader(is));
        RecursiveDescentParser parser = new RecursiveDescentParser(scanner);
        parser.parse();
        TruffleRuntime runtime = Truffle.getRuntime();
        System.out.println("Calling main function...");
        CallTarget callTarget = runtime.createCallTarget(parser.getRootNode());
        System.out.println(callTarget.call());
    }
}
