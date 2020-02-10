package org.truffle.cs.lisp.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.truffle.cs.lisp.parser.Token.Kind;

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

    public RecursiveDescentScanner(Reader r) {
        in = r;
        in = r;
        line = 1;
        col = 0;

        nextCh(); // read 1st char into ch, incr col to 1
        keywords = new HashMap<String, Token.Kind>();
        keywords.put(Kind.atom.label(), Kind.atom);
        keywords.put(Kind.eq.label(), Kind.eq);
        keywords.put(Kind.car.label(), Kind.car);
        keywords.put(Kind.cdr.label(), Kind.cdr);
        keywords.put(Kind.cons.label(), Kind.cons);
        keywords.put(Kind.if_.label(), Kind.if_);
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
                    // ch = EOF;
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
        // ----- start of new token
        Token t = new Token(Kind.none, line, col);

        switch (ch) {
            // ----- ident or keyword
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                readName(t);
                break;
            // ----- number
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                readNumber(t);
                break;
            // ----- character constant
            case '\'':
                readCharConst(t);
                break;
            // ----- simple tokens
            case '.':
                t.kind = Kind.period;
                nextCh();
                break;
            case '(':
                t.kind = Kind.lpar;
                nextCh();
                break;
            case ')':
                t.kind = Kind.rpar;
                nextCh();
                break;
            case EOF:
                t.kind = Kind.eof;
                break; // NO nextCh()!
            // ----- compound tokens
            case '+':
            	t.kind = Kind.plus;
                nextCh();
                break;
            case '-':
            	t.kind = Kind.minus;
                nextCh();
                break;
            case '*':
            	t.kind = Kind.times;
                nextCh();
                break;
            case '/':
            	t.kind = Kind.slash;
                nextCh();
                break;
            case '%':
            	t.kind = Kind.rem;
                nextCh();
                break;
            case '=':
            	t.kind = Kind.eql;
                nextCh();
                break;
            default:
                throw new Error("Invalid char " + ch);
                // nextCh();
                // break;
        } // end switch
        return t;
    }

    /** Reads a name into the <code>Token t</code>. */
    private void readName(Token t) {
        StringBuilder sb = new StringBuilder();
        sb.append(ch);
        nextCh();
        while (isLetter(ch) || isDigit(ch) || ch == '_') {
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

    /** Reads a number into the <code>Token t</code>. */
    private void readNumber(Token t) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(ch);
            nextCh();
        } while (isDigit(ch));
        t.kind = Kind.number;
        t.str = sb.toString();
        try {
            t.val = Integer.parseInt(t.str);
        } catch (NumberFormatException nfe) {
            throw new Error("Number too big " + t.str);
        }
    }

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
                case '\'': /* fall through */
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

    private boolean isLetter(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }
}