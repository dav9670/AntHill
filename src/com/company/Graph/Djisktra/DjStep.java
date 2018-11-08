package com.company.Graph.Djisktra;

import com.company.Graph.TunnelNode;

public class DjStep implements Comparable<DjStep> {
    public final TunnelNode node;
    public float totalWeight;
    public TunnelNode previous = null;

    public DjStep(TunnelNode node) {
        this.node = node;
        this.totalWeight = 0;
    }

    public DjStep(TunnelNode node, TunnelNode previous, float totalWeightBeforePrevious) {
        this.node = node;
        this.previous = previous;
        this.totalWeight = totalWeightBeforePrevious + node.getPosition().dist(previous.getPosition());
    }

    public TunnelNode getPrevious() {
        return previous;
    }

    public void setPrevious(TunnelNode _previous) {
        if (previous != null) {
            totalWeight -= node.getPosition().dist(previous.getPosition());
        }
        previous = _previous;
        if (previous != null) {
            totalWeight += node.getPosition().dist(previous.getPosition());
        }
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public float getWeightAddedToNode(TunnelNode targetNode) {
        return totalWeight + node.getPosition().dist(targetNode.getPosition());
    }

    @Override
    public int compareTo(DjStep o) {
        return Float.compare(this.getTotalWeight(), o.getTotalWeight());
    }
}