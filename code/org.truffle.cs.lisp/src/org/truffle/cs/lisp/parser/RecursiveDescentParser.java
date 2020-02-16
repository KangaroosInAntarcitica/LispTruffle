package org.truffle.cs.lisp.parser;

import org.truffle.cs.lisp.nodes.*;

import com.oracle.truffle.api.frame.FrameDescriptor;

import static org.truffle.cs.lisp.parser.Token.Kind.*;

import java.util.ArrayList;
import java.util.List;

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
        la = new Token(Token.Kind.undefined, 1, 1);
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
            throw new Error("Token " + expected + " expected");
        }
    }
    
    private LispRootNode rootNode;
    
    public LispRootNode getRootNode() {
    	return rootNode;
    }
    
    private LispCallableListNode readCallableList() {
        check(lpar);
    	ArrayList<LispExpressionNode> expressions = new ArrayList<LispExpressionNode>();
    	
    	while (sym != rpar) {
    		if (sym == plus || sym == minus || sym == times || sym == slash) {
    			LispArithmeticNode node = null;
    			switch(sym) {
					case plus: node = new LispArithmeticNode.LispAddNode(); break;
					case minus: node = new LispArithmeticNode.LispSubtractNode(); break;
					case times: node = new LispArithmeticNode.LispMultiplyNode(); break;
					case slash: node = new LispArithmeticNode.LispDivideNode(); break;
				}
				expressions.add(node);
                scan();
			}
    		else if (sym == number) {
                scan();
                LispNumberNode numberNode = new LispNumberNode(t.val);
                expressions.add(numberNode);
            }
    		else if (sym == lpar) {
                LispCallableListNode listNode = readCallableList();
                expressions.add(listNode);
            }
    		else {
    		    throw new Error("Unknown symbol");
    		}
    	}
    	check(rpar);
    	
    	List<LispExpressionNode> argumentsList = expressions.subList(1, expressions.size());
    	LispExpressionNode arguments[] = argumentsList.toArray(new LispExpressionNode[argumentsList.size()]);
    	if (argumentsList.size() == 0) 
    		throw new Error("Callable list shouldn't be empty");
    	return new LispCallableListNode(expressions.get(0), arguments);
    }
    
    public void parse() {
        scan(); // scan first symbol
        FrameDescriptor descriptor = new FrameDescriptor();
        rootNode = new LispRootNode(readCallableList(), descriptor);
        check(eof);
    }
}
