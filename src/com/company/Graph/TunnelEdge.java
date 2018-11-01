package com.company.Graph;

import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;

public class TunnelEdge extends DefaultEdge {

    public final float stepWidth = 20;
    public final int blockHp = 20;

    private List<Integer> edgeBlocks;

    public TunnelEdge(Double weight) {
        edgeBlocks = new ArrayList<>();
        for (int i = 0; i < weight + stepWidth; i += stepWidth) {
            edgeBlocks.add(blockHp);
        }
    }
}
