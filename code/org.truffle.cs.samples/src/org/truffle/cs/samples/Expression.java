package org.truffle.cs.samples;

import com.oracle.truffle.api.nodes.Node;

abstract class Expression extends Node {
    abstract int execute(int[] arguments);
}