package org.truffle.cs.lisp.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.truffle.cs.lisp.parser.Token.Kind;

import jdk.nashorn.internal.parser.TokenKind;

public final class RecursiveDescentScanner {
    protected static final char EOF = (char) -1;
    protected static final char LF = '\n';

    /** Input data to read from. */
    protected Reader in;

    /**
     * Lookahead character. (= next (unhandled) character in the input stream)
     */
    protected char ch;

    /** Current line in input stream. */
    protected int line;

    /** Current column in input stream. */
    protected int col;

    /** Mapping from keyword names to appropriate token codes. */
    private Map<String, Token.Kind> keywords;

    private void setKeywords(Kind[] kinds) {
        keywords = new HashMap<String, Token.Kind>();
        for (Kind kind: kinds) {
            keywords.put(kind.label(), kind);
        }
    }

    public RecursiveDescentScanner(Reader r) {
        in = r;
        line = 1;
        col = 0;
        nextCh(); // read 1st char into ch, incr col to 1

        Kind[] keys = {
                Token.Kind.nil, Token.Kind.period, Token.Kind.true_, Token.Kind.false_,
                Token.Kind.plus, Token.Kind.minus, Token.Kind.times, Token.Kind.slash,
                Token.Kind.equal, Token.Kind.notEqual, Token.Kind.greater, Token.Kind.greaterEqual, Token.Kind.less, Token.Kind.lessEqual,
                Token.Kind.assign, Token.Kind.if_,
        };
        setKeywords(keys);
    }

    /**
     * Reads next character from input stream into ch. Keeps pos, line and col in sync with reading
     * position.
     */
    private void nextCh() {
        try {
            ch = (char) in.read();
            switch (ch) {
                // No special handling for CR necessary. It is skipped in the
                // next() method because it is whitespace.
                case LF:
                    line++;
                    col = 0;
                    break;
                case EOF: // read returns -1 at end of file
                    ch = EOF;
                    break;
                default:
                    col++;
                    break;
            }
        } catch (IOException ioe) {
            ch = EOF;
        }
    }

    /**
     * Returns next token. To be used by parser.
     */
    public Token next() {
        while (Character.isWhitespace(ch)) {
            nextCh(); // skip whitespace
        }
        Token t = new Token(Kind.undefined, line, col);

        if (isDigit(ch)) {
            readNumber(t);
        } else if (isKeySymbol(ch)) {
            switch(ch) {
                case '(': t.kind = Kind.lpar; break;
                case ')': t.kind = Kind.rpar; break;
            }
            nextCh();
        } else if (ch == EOF) {
            t.kind = Kind.eof;
        } else {
            readName(t);
        }

        return t;
    }

    private void readName(Token t) {
        StringBuilder sb = new StringBuilder();
        sb.append(ch);
        nextCh();

        while (ch != EOF && !Character.isWhitespace(ch) && !isKeySymbol(ch)) {
            sb.append(ch);
            nextCh();
        }

        t.str = sb.toString();
        if (keywords.containsKey(t.str)) {
            t.kind = keywords.get(t.str);
        } else {
            t.kind = Kind.ident;
        }
    }

    private void readNumber(Token t) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(ch);
            nextCh();
        } while (isDigit(ch));
        t.kind = Kind.number;
        t.str = sb.toString();
        try {
            t.val = Float.parseFloat(t.str);
        } catch (NumberFormatException nfe) {
            throw new Error("Number too big " + t.str);
        }
    }

    /*
    private void readCharConst(Token t) {
        t.kind = Kind.charConst;
        nextCh();
        if (ch == '\'') {
            throw new Error("Empty char constant");
        } else if (ch == '\n' || ch == '\r') {
            throw new Error("Illegal line end");
        } else if (ch == '\\') {
            nextCh();
            switch (ch) {
                case 'n':
                    t.val = 10;
                    break;
                case 'r':
                    t.val = 13;
                    break;
                case '\'': // fall through
                case '\\':
                    t.val = ch;
                    break;
                default:
                    throw new Error("Undefined escape sequence");
            }
            nextCh();
            if (ch == '\'') {
                nextCh();
            } else {
                throw new Error("missing escape sequence");
            }
        } else {
            t.val = ch;
            nextCh();
            if (ch == '\'') {
                nextCh();
            } else {
                throw new Error("missing quote");
            }
        }
    }
    */

    private boolean isKeySymbol(char c) {
        return "()".indexOf(c) != -1;
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9' || c == '.';
    }
}