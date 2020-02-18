package org.truffle.cs.lisp.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.RootNode;
import org.truffle.cs.lisp.nodes.*;
import org.truffle.cs.lisp.types.LispCallable;

import java.util.*;
import java.util.function.Function;

import static org.truffle.cs.lisp.parser.Token.Kind.*;

public final class RecursiveDescentParser {
    /**
     * Maximum number of global variables per program
     */
    protected static final int MAX_GLOBALS = 32767;

    /**
     * Maximum number of fields per class
     */
    protected static final int MAX_FIELDS = 32767;

    /**
     * Maximum number of local variables per method
     */
    protected static final int MAX_LOCALS = 127;

    /**
     * Last recognized token;
     */
    protected Token t;

    /**
     * Lookahead token (not recognized).)
     */
    protected Token la;

    /**
     * Shortcut to kind attribute of lookahead token (la).
     */
    protected Token.Kind sym;

    /**
     * According scanner
     */
    public final RecursiveDescentScanner scanner;

    protected Stack<FrameDescriptor> descriptorStack;

    public RecursiveDescentParser(RecursiveDescentScanner scanner) {
        this.scanner = scanner;
        // Avoid crash when 1st symbol has scanner error.
        la = new Token(Token.Kind.undefined, 1, 1);
        scan();
    }

    /**
     * Reads ahead one symbol.
     */
    private void scan() {
        t = la;
        la = scanner.next();
        sym = la.kind;
    }

    /**
     * Verifies symbol and reads ahead.
     */
    private void check(Token.Kind expected) {
        if (sym == expected) {
            scan();
        } else {
            throw new Error("Token " + expected + " excpeted");
        }
    }

    private LispMainNode rootNode;

    public RootNode getRootNode() {
        return rootNode;
    }

    private LispExpressionNode readExpressionNode() {
        LispExpressionNode node;

        if (sym == ident) {
            scan();
            FrameSlot slot = null;
            int depth = 0;
            for (; depth < descriptorStack.size(); depth++) {
                slot = descriptorStack.get(descriptorStack.size() - depth - 1).findFrameSlot(t.str);
                if (slot != null) break;
            }
            if (slot == null) {
                throw new Error("Variable " + t.str + " not found");
            }
            node = LispVariableNodeGen.create(slot, depth);
        } else if (sym == number) {
            scan();
            node = new LispIntNode(t.val);
        } else if (sym == lpar) {
            node = readCallableList();
        } else {
            throw new Error("Unknown symbol");
        }

        return node;
    }

    private LispExpressionNode readDefineNode() {
        check(define);
        check(ident);
        FrameSlot frameSlot =  descriptorStack.peek().findOrAddFrameSlot(t.str);
        LispExpressionNode expression = readExpressionNode();

        return new LispDefineNode(frameSlot, expression);
    }

    private LispExpressionNode readLambdaNode() {
        check(lambda);
        List<String> argumentNames = new ArrayList<String>();
        if (sym == lpar) {
            scan();
            while (sym != rpar) {
                check(ident);
                argumentNames.add(t.str);
            }
            scan();
        } else if (sym == ident) {
            scan();
            argumentNames.add(t.str);
        } else {
            throw new Error("Lambda arguments expected");
        }

        descriptorStack.push(new FrameDescriptor());
        FrameSlot[] argumentSlots = new FrameSlot[argumentNames.size()];
        for (int i = 0; i < argumentNames.size(); ++i) {
            argumentSlots[i] = descriptorStack.peek().findOrAddFrameSlot(argumentNames.get(i));
        }

        LispExpressionNode bodyNode = readExpressionNode();

        LispCallable callable = LispCallable.createLambda(bodyNode, argumentSlots, descriptorStack.peek());
        LispExpressionNode node = LambdaNodeGen.create(callable);
        descriptorStack.pop();
        return node;
    }

    private LispExpressionNode readCallableList() {
        check(lpar);
        ArrayList<LispExpressionNode> expressions = new ArrayList<LispExpressionNode>();
        LispExpressionNode node;

        if (sym == define) {
            node = readDefineNode();
        } else if (sym == lambda) {
            node = readLambdaNode();
        } else {
            while (sym != rpar)
                expressions.add(readExpressionNode());

            LispExpressionNode[] arguments = expressions.subList(1, expressions.size()).toArray(new LispExpressionNode[]{});
            if (arguments.length == 0)
                throw new Error("Callable list shouldn't be empty");
            node = new LispCallableListNode(expressions.get(0), arguments);
        }

        check(rpar);
        return node;
    }

    public static HashMap<String, LispExpressionNode> builtIns = new HashMap<String, LispExpressionNode>();

    static {
        builtIns.put("+", new LispArithmeticNode.LispAddNode());
        builtIns.put("-", new LispArithmeticNode.LispSubtractNode());
        builtIns.put("*", new LispArithmeticNode.LispMultiplyNode());
        builtIns.put("/", new LispArithmeticNode.LispDivideNode());
        builtIns.put("println", new LispPrintNode());
    }

    public void parse() {
        FrameDescriptor descriptor = new FrameDescriptor();

        // Add the builtIns to global descriptor
        List<FrameSlot> slots = new ArrayList<>();
        List<LispCallable> callables = new ArrayList<>();
        for (Map.Entry<String, LispExpressionNode> builtIn: builtIns.entrySet()) {
            slots.add(descriptor.addFrameSlot(builtIn.getKey()));
            callables.add(LispCallable.create(builtIn.getValue(), descriptor));
        }

        descriptorStack = new Stack<>();
        descriptorStack.push(descriptor);

        List<LispExpressionNode> globalExpressionNodes = new ArrayList<LispExpressionNode>();
        while (sym != eof) globalExpressionNodes.add(readCallableList());

        scan(); // scan first symbol
        rootNode = new LispMainNode(
                globalExpressionNodes.toArray(new LispExpressionNode[]{}),
                descriptorStack.peek(),
                slots.toArray(new FrameSlot[]{}),
                callables.toArray(new LispCallable[]{}));
        check(eof);
    }

    public FrameDescriptor getLastDescriptor() {
        return descriptorStack.peek();
    }
}
