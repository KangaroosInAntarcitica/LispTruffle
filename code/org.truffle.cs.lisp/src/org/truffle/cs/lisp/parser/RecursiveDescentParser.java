package org.truffle.cs.lisp.parser;

import org.truffle.cs.lisp.nodes.LispIntNode;
import org.truffle.cs.lisp.nodes.LispListNode;
import org.truffle.cs.lisp.nodes.LispPlusNode;

import com.oracle.truffle.api.frame.FrameDescriptor;

import static org.truffle.cs.lisp.parser.Token.Kind.atom;
import static org.truffle.cs.lisp.parser.Token.Kind.car;
import static org.truffle.cs.lisp.parser.Token.Kind.cdr;
import static org.truffle.cs.lisp.parser.Token.Kind.charConst;
import static org.truffle.cs.lisp.parser.Token.Kind.cons;
import static org.truffle.cs.lisp.parser.Token.Kind.eof;
import static org.truffle.cs.lisp.parser.Token.Kind.eq;
import static org.truffle.cs.lisp.parser.Token.Kind.ident;
import static org.truffle.cs.lisp.parser.Token.Kind.if_;
import static org.truffle.cs.lisp.parser.Token.Kind.lpar;
import static org.truffle.cs.lisp.parser.Token.Kind.minus;
import static org.truffle.cs.lisp.parser.Token.Kind.number;
import static org.truffle.cs.lisp.parser.Token.Kind.period;
import static org.truffle.cs.lisp.parser.Token.Kind.plus;
import static org.truffle.cs.lisp.parser.Token.Kind.rem;
import static org.truffle.cs.lisp.parser.Token.Kind.rpar;
import static org.truffle.cs.lisp.parser.Token.Kind.slash;
import static org.truffle.cs.lisp.parser.Token.Kind.times;

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
    
    private LispListNode rootList;
    
    public LispListNode getRootNode() {
    	return rootList;
    }
    
    private LispListNode readList(FrameDescriptor frame) {
    	LispListNode list = new LispListNode(frame);
    	while (sym != rpar) {
    		switch (sym) {
    			case plus:
    				LispPlusNode plusNode = new LispPlusNode(frame);
    				list.addElement(plusNode);
    				break;
    			case number:
    				LispIntNode intNode = new LispIntNode(frame, t.val);
    				list.addElement(intNode);
    				break;
    			case lpar:
    				LispListNode listNode = readList(frame);
    				list.addElement(listNode);
    				break;
				default:
					throw new Error("Unknown symbol");
    		}
    		scan();
    	}
    	return list;
    }
    
    public void parse() {
        scan(); // scan first symbol
        check(lpar);
        rootList = readList(null);
    }
}
