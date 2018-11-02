package com.company.Graph;

import processing.core.PVector;

public class TunnelNode {

    public final NodeType nodeType;
    private PVector position;
    public TunnelNode(PVector position, NodeType nodeType) {
        this.position = position;
        this.nodeType = nodeType;
    }

    public PVector getPosition() {
        return position.copy();
    }

    public enum NodeType {
        ENTRY(75),
        POI(100),
        CROSSROAD(0),
        CHAMBER(150);

        public final float radius;

        NodeType(float radius) {
            this.radius = radius;
        }
    }
}
