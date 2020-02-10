package org.truffle.cs.samples.PlainAST;

public class PlainAST {

    interface Node {
        int execute();
    }

    public static class AddNode implements Node {
        final Node arg1, arg2;

        public AddNode(Node arg1, Node arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public int execute() {
            return arg1.execute() + arg2.execute();
        }
    }

    public static class ConstNode implements Node {
        private int val;

        public ConstNode(int val) {
            this.val = val;
        }

        public int execute() {
            return val;
        }
    }

    public static void main(String[] args) {
        System.out.println("Result of 1+1=" + new AddNode(new ConstNode(1), new ConstNode(1)).execute());
    }

}
