package org.truffle.cs.lisp.main;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import org.truffle.cs.lisp.parser.RecursiveDescentScanner;
import org.truffle.cs.lisp.parser.RecursiveDescentParser;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleRuntime;

public class LispRuntime {
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            runScript(args[0]);
        } else {
            runREPL();
        }
    }

    static void runScript(String filename) throws IOException {
        File script = new File(filename);
        byte[] fileContent = Files.readAllBytes(script.toPath());
        InputStream is = new ByteArrayInputStream(fileContent);
        RecursiveDescentScanner scanner = new RecursiveDescentScanner(new InputStreamReader(is));
        RecursiveDescentParser parser = new RecursiveDescentParser(scanner);
        parser.parse();
        TruffleRuntime runtime = Truffle.getRuntime();
        CallTarget callTarget = runtime.createCallTarget(parser.getRootNode());
        for (int i = 0; i < 10_000; i++) {
        	callTarget.call();
        }
        System.out.println();
        System.out.println(callTarget.call());
    }

    static void runREPL() {
        Scanner in = new Scanner(System.in);
        while (Boolean.TRUE) {
            System.out.print("> ");
            if (in.hasNextLine()) {
                String code = in.nextLine();

            if (code.isEmpty()) {
                continue;
            }

            InputStream is = new ByteArrayInputStream(code.getBytes());
            RecursiveDescentScanner scanner = new RecursiveDescentScanner(new InputStreamReader(is));
            RecursiveDescentParser parser = new RecursiveDescentParser(scanner);
            parser.parse();
            TruffleRuntime runtime = Truffle.getRuntime();
            CallTarget callTarget = runtime.createCallTarget(parser.getRootNode());
            System.out.println(callTarget.call());
            } else {
                System.out.println("\nBye.");
                break;
            }
        }
    }
}
