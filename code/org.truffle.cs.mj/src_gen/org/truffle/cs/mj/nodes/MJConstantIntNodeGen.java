// CheckStyle: start generated
package org.truffle.cs.mj.nodes;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import org.truffle.cs.mj.nodes.MJConstantIntNode;

@GeneratedBy(MJConstantIntNode.class)
public final class MJConstantIntNodeGen extends MJConstantIntNode {

    private MJConstantIntNodeGen(int constant) {
        super(constant);
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        return doInt();
    }

    @Override
    public int executeI32(VirtualFrame frameValue) {
        return doInt();
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.MONOMORPHIC;
    }

    public static MJConstantIntNode create(int constant) {
        return new MJConstantIntNodeGen(constant);
    }

}
