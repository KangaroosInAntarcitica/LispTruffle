package org.truffle.cs.mj.parser;

/**
 * A <code>Token</code> represents a terminal symbol. Tokens are provided by the scanner for the
 * parser. They hold additional information about the symbol.
 */
public class Token {
    public enum Kind {

        // @formatter:off
		none("none"),
		ident("identifier"),
		number("number"),
		charConst("character constant"),
		plus("+"),
		minus("-"),
		times("*"),
		slash("/"),
		rem("%"),
		eql("=="),
		neq("!="),
		lss("<"),
		leq("<="),
		gtr(">"),
		geq(">="),
		and("&&"),
		or("||"),
		assign("="),
		plusas("+="),
		minusas("-="),
		timesas("*="),
		slashas("/="),
		remas("%="),
		pplus("++"),
		mminus("--"),
		semicolon(";"),
		comma(","),
		period("."),
		lpar("("),
		rpar(")"),
		lbrack("["),
		rbrack("]"),
		lbrace("{"),
		rbrace("}"),
		break_("break"),
		continue_("continue"),
		class_("class"),
		else_("else"),
		final_("final"),
		if_("if"),
		new_("new"),
		print("print"),
        abs("abs"),
		program("program"),
		read("read"),
		return_("return"),
		void_("void"),
		while_("while"),
		eof("end of file");
		// @formatter:on

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
    public int val;

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
        } else if (kind == Kind.charConst) {
            result = result + ", val '" + (char) val + "'";
        }
        return result;
    }
}
