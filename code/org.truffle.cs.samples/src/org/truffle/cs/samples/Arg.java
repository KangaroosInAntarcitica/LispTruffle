package org.truffle.cs.samples;

public class Arg extends Expression {
    final int index;

    public Arg(int index) {
        this.index = index;
    }

    @Override
    int execute(int[] arguments) {
        return arguments[index];
    }
}