package org.truffle.cs.lisp.parser;

/**
 * A <code>Token</code> represents a terminal symbol. Tokens are provided by the scanner for the
 * parser. They hold additional information about the symbol.
 */
public class Token {
    public enum Kind {

        // @formatter:off
        undefined("undefined"),

        eof("end of file"),
        lpar("("),
        rpar(")"),

        number("__number__"),
        ident("__identifier__"),
        nil("nil"),
        true_("TRUE"),
        false_("FALSE"),

        period("."),
        plus("+"),
        minus("-"),
        times("*"),
        slash("/"),
        not("not"), not_("not"),
        equal("=="), equal_("eq"),
        notEqual("!="),
        greater(">"),
        greaterEqual(">="),
        less("<"),
        lessEqual("<="),
        and("&&"), and_("and"),
        or("||"), or_("or"),

        assign("="),
        if_("if");

//        car("car"),
//        cdr("cdr"),
//        cons("cons");

        private String label;

        private Kind(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public String label() {
            return label;
        }
    }

    /** Token class (none, ident, ...). */
    public Kind kind;

    /** Line number of this token. */
    public final int line;

    /** Column number of this token. */
    public final int col;

    /** Value of this token (for numbers or character constants). */
    public float val;

    /** String representation of this token. */
    public String str;

    /**
     * Constructor that sets the fields required for all tokens.
     */
    public Token(Kind kind, int line, int col) {
        this.kind = kind;
        this.line = line;
        this.col = col;
    }

    /**
     * Returns a string representation of this Token object. This only includes the relevant
     * attributes of the token.
     */
    @Override
    public String toString() {
        String result = "line " + line + ", col " + col + ", kind " + kind;
        if (kind == Kind.ident) {
            result = result + ", str " + str;
        } else if (kind == Kind.number) {
            result = result + ", val " + val;
        }
        return result;
    }
}
