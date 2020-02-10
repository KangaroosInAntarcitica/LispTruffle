package org.truffle.cs.samples;

public class Add extends Expression {
    @Child Expression left;
    @Child Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    int execute(int[] arguments) {
        return this.left.execute(arguments) + this.right.execute(arguments);
    }
}