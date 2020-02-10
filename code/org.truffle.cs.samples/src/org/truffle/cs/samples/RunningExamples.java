package org.truffle.cs.samples;

public class RunningExamples {

    static A a;

    static class A {
        Object o;
    }

    public static void main(String[] args) {
        a = new A();
        // some work
        a = null;
    }
}
