package com.company.Graph;

import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;

public class TunnelEdge extends DefaultEdge {

    public final TunnelNode firstNode;
    public final TunnelNode secondNode;

    private final float weight;

    public final float stepWidth = 20;
    public final int blockHp = 20;

    private List<Integer> edgeBlocks;

    public TunnelEdge(TunnelNode _firstNode, TunnelNode _secondNode) {
        firstNode = _firstNode;
        secondNode = _secondNode;
        weight = firstNode.getPosition().dist(secondNode.getPosition());

        edgeBlocks = new ArrayList<>();
        for (int i = 0; i < weight + stepWidth; i += stepWidth) {
            edgeBlocks.add(blockHp);
        }
    }

    public float getWeight() {
        return weight;
    }
}
