package org.truffle.cs.lisp.types;

public class LispList {
    Object value;
    LispList rest;
    int length;

    public Object getValue() {
        return value;
    }

    public LispList getRest() {
        return rest;
    }

    public int getLength() {
        return length;
    }

    public boolean empty() {
        return length != 0;
    }

    public Object getAtIndex(int index) {
        LispList list = this;

        while (index != 0) {
            if (list.length == 0)
                throw new RuntimeException("Index " + index + " does not exist in list");
            list = list.rest;
            index--;
        }
        return list.value;
    }
}
