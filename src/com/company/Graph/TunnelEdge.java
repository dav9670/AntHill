package com.company.Graph;

import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;

public class TunnelEdge extends DefaultEdge {

    public final float stepWidth = 20;
    public final int blockHp = 20;

    public final TunnelNode firstNode;
    public final TunnelNode secondNode;

    private List<Integer> edgeBlocks;

    public TunnelEdge(TunnelNode _firstNode, TunnelNode _secondNode) {
        firstNode = _firstNode;
        secondNode = _secondNode;

        edgeBlocks = new ArrayList<>();
        for (int i = 0; i < firstNode.getPosition().dist(secondNode.getPosition()) + stepWidth; i += stepWidth) {
            edgeBlocks.add(blockHp);
        }
    }
}
