package org.truffle.cs.mj.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.truffle.cs.mj.parser.Token.Kind;

public final class RecursiveDescendScanner {
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

    // TODO Exercise 2: implementation of scanner

    /** Mapping from keyword names to appropriate token codes. */
    private Map<String, Token.Kind> keywords;

    public RecursiveDescendScanner(Reader r) {
        in = r;
        in = r;
        line = 1;
        col = 0;

        nextCh(); // read 1st char into ch, incr col to 1
        keywords = new HashMap<String, Token.Kind>();
        keywords.put(Kind.break_.label(), Kind.break_);
        keywords.put(Kind.continue_.label(), Kind.continue_);
        keywords.put(Kind.class_.label(), Kind.class_);
        keywords.put(Kind.else_.label(), Kind.else_);
        keywords.put(Kind.final_.label(), Kind.final_);
        keywords.put(Kind.if_.label(), Kind.if_);
        keywords.put(Kind.new_.label(), Kind.new_);
        keywords.put(Kind.print.label(), Kind.print);
        keywords.put(Kind.program.label(), Kind.program);
        keywords.put(Kind.read.label(), Kind.read);
        keywords.put(Kind.return_.label(), Kind.return_);
        keywords.put(Kind.void_.label(), Kind.void_);
        keywords.put(Kind.while_.label(), Kind.while_);
        keywords.put(Kind.abs.label(), Kind.abs);
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
            case ';':
                t.kind = Kind.semicolon;
                nextCh();
                break;
            case ',':
                t.kind = Kind.comma;
                nextCh();
                break;
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
            case '[':
                t.kind = Kind.lbrack;
                nextCh();
                break;
            case ']':
                t.kind = Kind.rbrack;
                nextCh();
                break;
            case '{':
                t.kind = Kind.lbrace;
                nextCh();
                break;
            case '}':
                t.kind = Kind.rbrace;
                nextCh();
                break;
            case EOF:
                t.kind = Kind.eof;
                break; // NO nextCh()!
            // ----- compound tokens
            case '+':
                nextCh();
                if (ch == '+') {
                    t.kind = Kind.pplus;
                    nextCh();
                } else if (ch == '=') {
                    t.kind = Kind.plusas;
                    nextCh();
                } else {
                    t.kind = Kind.plus;
                }
                break;
            case '-':
                nextCh();
                if (ch == '-') {
                    t.kind = Kind.mminus;
                    nextCh();
                } else if (ch == '=') {
                    t.kind = Kind.minusas;
                    nextCh();
                } else {
                    t.kind = Kind.minus;
                }
                break;
            case '*':
                nextCh();
                if (ch == '=') {
                    t.kind = Kind.timesas;
                    nextCh();
                } else {
                    t.kind = Kind.times;
                }
                break;
            case '%':
                nextCh();
                if (ch == '=') {
                    t.kind = Kind.remas;
                    nextCh();
                } else {
                    t.kind = Kind.rem;
                }
                break;
            case '/':
                nextCh();
                if (ch == '*') {
                    skipComment(t);
                    t = next(); // recursion !
                } else if (ch == '=') {
                    t.kind = Kind.slashas;
                    nextCh();
                } else {
                    t.kind = Kind.slash;
                }
                break;
            case '=':
                nextCh();
                if (ch == '=') {
                    t.kind = Kind.eql;
                    nextCh();
                } else {
                    t.kind = Kind.assign;
                }
                break;
            case '!':
                nextCh();
                if (ch == '=') {
                    t.kind = Kind.neq;
                    nextCh();
                } else {
                    throw new Error("Invalid character !");
                }
                break;
            case '>':
                nextCh();
                if (ch == '=') {
                    t.kind = Kind.geq;
                    nextCh();
                } else {
                    t.kind = Kind.gtr;
                }
                break;
            case '<':
                nextCh();
                if (ch == '=') {
                    t.kind = Kind.leq;
                    nextCh();
                } else {
                    t.kind = Kind.lss;
                }
                break;
            case '&':
                nextCh();
                if (ch == '&') {
                    t.kind = Kind.and;
                    nextCh();
                } else {
                    throw new Error("Invalid char &");
                }
                break;
            case '|':
                nextCh();
                if (ch == '|') {
                    t.kind = Kind.or;
                    nextCh();
                } else {
                    throw new Error("Invalid char |");
                }
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

    /** Skips nested multi-line comments. */
    private void skipComment(Token t) {
        int openComments = 1;
        nextCh();
        do {
            switch (ch) {
                case '/':
                    nextCh();
                    if (ch == '*') {
                        openComments++; // nested comment
                        nextCh();
                    }
                    break;
                case '*':
                    nextCh();
                    if (ch == '/') {
                        openComments--; // comment closed
                        nextCh();
                    }
                    break;
                default:
                    nextCh();
                    break;
            }
        } while (ch != EOF && openComments > 0);

        if (ch == EOF && openComments > 0) {
            throw new Error("EOF in comment");
        }
    }

    private boolean isLetter(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    private boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }
}