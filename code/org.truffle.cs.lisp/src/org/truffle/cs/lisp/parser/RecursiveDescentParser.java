package org.truffle.cs.lisp.parser;

import org.truffle.cs.lisp.nodes.*;

import com.oracle.truffle.api.frame.FrameDescriptor;

import static org.truffle.cs.lisp.parser.Token.Kind.*;

import java.util.ArrayList;

public final class RecursiveDescentParser {
    /** Maximum number of global variables per program */
    protected static final int MAX_GLOBALS = 32767;

    /** Maximum number of fields per class */
    protected static final int MAX_FIELDS = 32767;

    /** Maximum number of local variables per method */
    protected static final int MAX_LOCALS = 127;

    /** Last recognized token; */
    protected Token t;

    /** Lookahead token (not recognized).) */
    protected Token la;

    /** Shortcut to kind attribute of lookahead token (la). */
    protected Token.Kind sym;

    /** According scanner */
    public final RecursiveDescentScanner scanner;

    public RecursiveDescentParser(RecursiveDescentScanner scanner) {
        this.scanner = scanner;
        // Avoid crash when 1st symbol has scanner error.
        la = new Token(Token.Kind.none, 1, 1);
    }

    /** Reads ahead one symbol. */
    private void scan() {
        t = la;
        la = scanner.next();
        sym = la.kind;
    }

    /** Verifies symbol and reads ahead. */
    private void check(Token.Kind expected) {
    	if (sym == expected) {
            scan();
        } else {
            throw new Error("Token " + expected + " excpeted");
        }
    }
    
    private LispRootNode rootNode;
    
    public LispRootNode getRootNode() {
    	return rootNode;
    }
    
    private LispCallableListNode readCallableList() {
    	ArrayList<LispExpressionNode> expressions = new ArrayList<LispExpressionNode>();
    	
    	while (sym != rpar) {
    		switch (sym) {
    			case plus:
    				scan();
    				LispArithmeticNode addNode = new LispArithmeticNode.LispAddNode();
    				expressions.add(addNode);
    				break;
    			case minus:
    				scan();
    				LispArithmeticNode subtractNode = new LispArithmeticNode.LispSubtractNode();
    				expressions.add(subtractNode);
    				break;
    			case times:
    				scan();
    				LispArithmeticNode multiplyNode = new LispArithmeticNode.LispMultiplyNode();
    				expressions.add(multiplyNode);
    				break;
    			case slash:
    				scan();
    				LispArithmeticNode divideNode = new LispArithmeticNode.LispDivideNode();
    				expressions.add(divideNode);
    				break;
    			case number:
    				scan();
    				LispIntNode intNode = new LispIntNode(t.val);
    				expressions.add(intNode);
    				break;
    			case lpar:
    				scan();
    				LispCallableListNode listNode = readCallableList();
    				expressions.add(listNode);
    				break;
				default:
					throw new Error("Unknown symbol");
    		}
    	}
    	
    	return new LispCallableListNode(expressions);
    }
    
    public void parse() {
        scan(); // scan first symbol
        check(lpar);
        FrameDescriptor descriptor = new FrameDescriptor();
        rootNode = new LispRootNode(readCallableList(), descriptor);
    }
}
