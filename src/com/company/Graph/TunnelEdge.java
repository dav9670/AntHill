package com.company.Graph;

import org.jgrapht.graph.DefaultEdge;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TunnelEdge extends DefaultEdge {

    public final TunnelNode firstNode;
    public final TunnelNode secondNode;

    private final float weight;

    private Color color;

    public final float stepWidth = 20;
    public final int blockHp = 20;

    private List<Integer> edgeBlocks;


    public TunnelEdge(TunnelNode _firstNode, TunnelNode _secondNode) {
        firstNode = _firstNode;
        secondNode = _secondNode;
        weight = firstNode.getPosition().dist(secondNode.getPosition());

        color = Color.BLACK;

        edgeBlocks = new ArrayList<>();
        for (int i = 0; i < weight + stepWidth; i += stepWidth) {
            edgeBlocks.add(blockHp);
        }
    }

    public float getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
