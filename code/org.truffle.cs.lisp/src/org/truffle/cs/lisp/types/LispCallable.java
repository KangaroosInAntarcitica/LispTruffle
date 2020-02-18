package org.truffle.cs.lisp.types;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.MaterializedFrame;
import org.truffle.cs.lisp.nodes.LispExpressionNode;
import org.truffle.cs.lisp.nodes.LispLambdaRootNode;
import org.truffle.cs.lisp.nodes.LispRootNode;

public class LispCallable {
    private RootCallTarget target;
    private MaterializedFrame frame;

    public LispCallable(RootCallTarget target) {
        this.target = target;
    }

    public RootCallTarget getCallTarget() {
        return target;
    }

    public void setFrame(MaterializedFrame frame) {
        this.frame = frame;
    }

    public MaterializedFrame getFrame() {
        return frame;
    }

    public static LispCallable create(LispExpressionNode node, FrameDescriptor descriptor) {
        LispRootNode root = new LispRootNode(node, descriptor);
        return new LispCallable(Truffle.getRuntime().createCallTarget(root));
    };

    public static LispCallable createLambda(LispExpressionNode node, FrameSlot[] argumentSlots, FrameDescriptor descriptor) {
        LispLambdaRootNode root = new LispLambdaRootNode(node, descriptor, argumentSlots);
        return new LispCallable(Truffle.getRuntime().createCallTarget(root));
    }
}
